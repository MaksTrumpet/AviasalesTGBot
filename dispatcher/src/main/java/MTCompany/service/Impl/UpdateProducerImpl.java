package MTCompany.service.Impl;

import MTCompany.request.model.UserRequestModel;
import MTCompany.service.UpdateProducer;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Log4j
@Service
public class UpdateProducerImpl implements UpdateProducer {
    private final RabbitTemplate rabbitTemplate;

    public UpdateProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String rabbitQueue, UserRequestModel userRequestModel) {
        log.debug(userRequestModel);
        rabbitTemplate.convertAndSend(rabbitQueue, userRequestModel);
    }
}
