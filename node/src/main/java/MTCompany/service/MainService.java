package MTCompany.service;

import MTCompany.entity.model.UserRequestModel;

public interface MainService {
    void processOneDayOneCity(UserRequestModel userRequestModel, String rabbitQueue);
}
