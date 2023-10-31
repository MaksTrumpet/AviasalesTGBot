package MTCompany.service.impl;

import MTCompany.entity.UserRequestModel;
import MTCompany.service.ConsumerService;
import MTCompany.service.MainService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Service;

import static MTCompany.model.RabbitQueue.ONE_DATE_ONE_CITY_REQUEST;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final MainService mainService;

    public ConsumerServiceImpl(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = ONE_DATE_ONE_CITY_REQUEST)
    public void oneDayOneCityUpdate(UserRequestModel userRequestModel) {
        log.debug("NODE: oneDayOneCityUpdate queue is received");
        mainService.processOneDayOneCity(userRequestModel);
    }
}
