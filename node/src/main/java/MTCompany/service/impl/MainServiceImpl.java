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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        LocalDate localDepartureDate = userRequestModel.getDepartureDate();
        LocalDateTime startOfDay = localDepartureDate.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return ticketDao.findAndReturnByOriginCityNameAndDestinationCityNameAndLocalDepartureDate(
                userRequestModel.getDepartureCity(), userRequestModel.getArrivalCity(), startOfDay, endOfDay);
    }

    private void delegateModelCreateByRabbitMq(List<Ticket> tickets, Long chatId, String rabbitQueue) {
        if (!tickets.isEmpty()) {
            switch (rabbitQueue) {
                case ONE_DATE_ONE_CITY_REQUEST:
                    sendAnswer(UserResponseModel.createModelResponseForOneDayOneCityRequest(tickets, chatId));
                    log.debug("Tickets is send ");

                    break;
                default:
                    log.error("RabbitQueue is unknown");
            }
        } else {
            log.debug("Tickets is empty");
        }
    }


    private void sendAnswer(List<UserResponseModel> responseModelList) {
        for (UserResponseModel responseModel : responseModelList) {
            producerService.produceAnswer(responseModel);
        }
    }
}
