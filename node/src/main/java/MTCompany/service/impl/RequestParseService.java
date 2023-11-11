package MTCompany.service.impl;

import MTCompany.dao.RequestParseRepository;
import MTCompany.dao.TicketsRepository;
import MTCompany.entity.RequestParse;
import MTCompany.entity.Ticket;

import MTCompany.entity.model.UserRequestModel;
import MTCompany.entity.model.UserResponseModel;
import MTCompany.service.MainService;
import MTCompany.service.ProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import java.util.concurrent.PriorityBlockingQueue;


@Log4j
@Service
public class RequestParseService implements MainService {
    private final PriorityBlockingQueue<UserRequestModel> userRequests;
    private final RequestParseRepository requestParseRepository;
    private final TicketsRepository ticketsRepository;
    private final ProducerService producerService;

    public RequestParseService(RequestParseRepository requestParseRepository, TicketsRepository ticketsRepository, ProducerService produceService) {
        this.requestParseRepository = requestParseRepository;
        this.ticketsRepository = ticketsRepository;
        this.producerService = produceService;
        this.userRequests = new PriorityBlockingQueue<>();
    }


    public void processOneDayOneCity(UserRequestModel userRequestModel, String rabbitQueue) {
        System.out.println(userRequestModel);
        Optional<RequestParse> optionalRequestParse = findRequest(userRequestModel);
        RequestParse requestParse = optionalRequestParse.orElseGet(() -> createNewRequestParse(userRequestModel));

        optionalRequestParse.ifPresentOrElse(
                existingRequestParse -> requestParse.setIs_monitoring(
                        changeSubscribes(existingRequestParse.getIs_monitoring(), userRequestModel.getIs_monitoring())
                ),
                () -> requestParse.setIs_monitoring(0)
        );

        requestParseRepository.save(requestParse);//TODO не сохраняет запросы, пока другие обрабатываются

        userRequests.add(userRequestModel);
        checkTicketsAndNotifyUser();
    }


    @Async
    public void checkTicketsAndNotifyUser() {
        while (!userRequests.isEmpty()) {
            UserRequestModel userRequestModel = userRequests.peek();
            if (userRequestModel != null) {
                List<Ticket> tickets = findTickets(userRequestModel);
                if (!tickets.isEmpty()) {
                    List<UserResponseModel> responseModelList = UserResponseModel
                            .createModelResponseForOneDayOneCityRequest(tickets, userRequestModel.getChatId());
                    sendAnswer(responseModelList);
                    userRequests.poll();
                }
            }
        }
    }


    private int changeSubscribes(int actualSubscribeState, int newSubscribe) {
        return actualSubscribeState + newSubscribe;
    }

    private RequestParse createNewRequestParse(UserRequestModel userRequestModel) {
        RequestParse newRequestParse = new RequestParse();
        newRequestParse.setDepartureCityIATA(userRequestModel.getDepartureCity());
        newRequestParse.setArrivalCityIATA(userRequestModel.getArrivalCity());
        newRequestParse.setDepartureDate(userRequestModel.getDepartureDate());
        return newRequestParse;
    }

    private List<Ticket> findTickets(UserRequestModel userRequestModel) {
        return ticketsRepository.findByOriginIataAndDestinationIataAndDepartureDate(
                userRequestModel.getDepartureCity(), userRequestModel.getArrivalCity(), userRequestModel.getDepartureDate());
    }

    private Optional<RequestParse> findRequest(UserRequestModel userRequestModel) {
        return requestParseRepository.findByDepartureCityIATAAndArrivalCityIATAAndDepartureDate(
                userRequestModel.getDepartureCity(), userRequestModel.getArrivalCity(), userRequestModel.getDepartureDate());
    }

    private void sendAnswer(List<UserResponseModel> userResponseModelList) {
        userResponseModelList.forEach(producerService::produceAnswer);
    }
}
