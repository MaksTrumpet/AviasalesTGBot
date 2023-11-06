package MTCompany.service;


import MTCompany.model.UserResponseModel;

public interface AnswerService {
    void answerProcess(UserResponseModel userRequestModel, String rabbitQueue);
}
