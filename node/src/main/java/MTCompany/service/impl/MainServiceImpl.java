package MTCompany.service.impl;

import MTCompany.dao.TicketDao;
import MTCompany.dao.UserRequestDataDao;
import MTCompany.entity.Ticket;
import MTCompany.entity.UserRequestData;

import MTCompany.entity.model.UserRequestModel;
import MTCompany.entity.model.UserResponseModel;
import MTCompany.service.MainService;
import MTCompany.service.ProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static MTCompany.model.RabbitQueue.ONE_DATE_ONE_CITY_REQUEST;

@Log4j
@Service
public class MainServiceImpl implements MainService {
    private final UserRequestDataDao userRequestDataDao;
    private final TicketDao ticketDao;
    private final ProducerService producerService;

    public MainServiceImpl(UserRequestDataDao userRequestDataDao, TicketDao ticketDao, ProducerService produceService) {
        this.userRequestDataDao = userRequestDataDao;
        this.ticketDao = ticketDao;
        this.producerService = produceService;
    }


    public void processOneDayOneCity(UserRequestModel userRequestModel, String rabbitQueue) {
        Long chatId = userRequestModel.getChatId();
        saveUserRequestData(userRequestModel);
        List<Ticket> tickets = searchTicketsInDB(userRequestModel);
        delegateModelCreateByRabbitMq(tickets, chatId, rabbitQueue);
    }

    private void saveUserRequestData(UserRequestModel userRequestModel) {
        UserRequestData userRequestData = UserRequestData.builder()
                .chatId(userRequestModel.getChatId())
                .departureCity(userRequestModel.getDepartureCity())
                .arrivalCity(userRequestModel.getArrivalCity())
                .departureDate(userRequestModel.getDepartureDate())
                .build();
        userRequestDataDao.save(userRequestData);
    }

    public List<Ticket> searchTicketsInDB(UserRequestModel userRequestModel) {
        List<Ticket> tickets;
        LocalDateTime departureDateTime = userRequestModel.getDepartureDate().atStartOfDay();
        tickets = ticketDao.findAndReturnByOriginCityNameAndDestinationCityNameAndLocalDepartureDateTime(
                userRequestModel.getDepartureCity(), userRequestModel.getArrivalCity(), departureDateTime);

        log.debug(tickets);


        return tickets;
    }

    private void delegateModelCreateByRabbitMq(List<Ticket> tickets, Long chatId, String rabbitQueue) {
        if (!tickets.isEmpty()) {
            switch (rabbitQueue) {
                case ONE_DATE_ONE_CITY_REQUEST:
                    createModelResponseForOneDayOneCityRequest(tickets, chatId);
                default:
                    log.error("RabbitQueue is unknown");
            }
        }
    }

    private void createModelResponseForOneDayOneCityRequest(List<Ticket> tickets, Long chatId) {
        List<UserResponseModel> responseModelList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            UserResponseModel userResponseModel = UserResponseModel.builder()
                    .chatId(chatId)
                    .ticketId(ticket.getTicketId())
                    .proposalId(ticket.getProposalId())
                    .pricePerPersonValue(ticket.getPricePerPersonValue())
                    .airlineId(ticket.getAirlineId())
                    .flightNumber(ticket.getFlightNumber())
                    .handbags(ticket.getHandbags())
                    .baggage(ticket.getBaggage())
                    .originAirport(ticket.getOriginAirport())
                    .destinationAirport(ticket.getDestinationAirport())
                    .localDepartureDateTime(ticket.getLocalDepartureDateTime())
                    .localArrivalDateTime(ticket.getLocalArrivalDateTime())
                    .originAirportName(ticket.getOriginAirportName())
                    .originCity(ticket.getOriginCity())
                    .originCityName(ticket.getOriginCityName())
                    .originCountry(ticket.getOriginCountry())
                    .originCountryName(ticket.getOriginCountryName())
                    .destinationAirportName(ticket.getDestinationAirportName())
                    .destinationCity(ticket.getDestinationCity())
                    .destinationCityName(ticket.getDestinationCityName())
                    .destinationCountry(ticket.getDestinationCountry())
                    .destinationCountryName(ticket.getDestinationCountryName())
                    .airlineName(ticket.getAirlineName())
                    .mainOrigin(ticket.getMainOrigin())
                    .mainDestination(ticket.getMainDestination())
                    .build();

            responseModelList.add(userResponseModel);
        }
        sendAnswer(responseModelList);
    }


    private void sendAnswer(List<UserResponseModel> responseModelList) {
        for (UserResponseModel responseModel : responseModelList) {
            producerService.produceAnswer(responseModel);
        }
    }
}
