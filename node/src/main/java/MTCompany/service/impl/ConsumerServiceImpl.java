package MTCompany.service.impl;

import MTCompany.entity.model.UserRequestModel;
import MTCompany.service.ConsumerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Service;

import static MTCompany.model.RabbitQueue.ONE_DATE_ONE_CITY_REQUEST;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    public ConsumerServiceImpl(RequestParseService requestParseService) {
        this.requestParseService = requestParseService;
    }

    private final RequestParseService requestParseService;

    @Override
    @RabbitListener(queues = ONE_DATE_ONE_CITY_REQUEST)
    public void oneDayOneCityUpdate(UserRequestModel userRequestModel) {
        log.debug("NODE: oneDayOneCityUpdate queue is received");
        requestParseService.processOneDayOneCity(userRequestModel, ONE_DATE_ONE_CITY_REQUEST);
    }
}
