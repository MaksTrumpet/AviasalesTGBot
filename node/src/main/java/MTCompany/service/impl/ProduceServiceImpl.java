package MTCompany.service.impl;

import MTCompany.entity.Ticket;
import MTCompany.entity.model.UserResponseModel;
import MTCompany.service.ProducerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static MTCompany.model.RabbitQueue.ANSWER_MESSAGE;

@Service
public class ProduceServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    public ProduceServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(UserResponseModel userResponseModel) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, userResponseModel);
    }
}
