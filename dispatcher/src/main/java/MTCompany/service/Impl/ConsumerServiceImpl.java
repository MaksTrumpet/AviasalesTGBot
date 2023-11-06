package MTCompany.service.Impl;


import MTCompany.model.UserResponseModel;
import MTCompany.service.AnswerService;
import MTCompany.service.ConsumerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static MTCompany.model.RabbitQueue.ANSWER_MESSAGE;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final AnswerService answerService;

    public ConsumerServiceImpl(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void answerUpdate(UserResponseModel userResponseModel) {
        log.debug("Dispatcher: oneDayOneCityUpdate queue is received");
        answerService.answerProcess(userResponseModel, ANSWER_MESSAGE);
    }
}
