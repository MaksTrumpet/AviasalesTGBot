package MTCompany.service.Impl;

import MTCompany.controller.UpdateController;
import MTCompany.model.UserResponseModel;
import MTCompany.service.AnswerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final UpdateController updateController;

    public AnswerServiceImpl(UpdateController updateController) {
        this.updateController = updateController;
    }


    @Override
    public void answerProcess(UserResponseModel userResponseModel, String rabbitQueue) {
        String output = userResponseModel.createAnswer();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userResponseModel.getChatId());
        sendMessage.setText(output);
        updateController.setView(sendMessage);
    }
}
