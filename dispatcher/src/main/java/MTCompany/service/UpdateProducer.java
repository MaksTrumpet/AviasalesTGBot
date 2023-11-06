package MTCompany.service;

import MTCompany.model.UserRequestModel;


public interface UpdateProducer {
    void produce(String rabbitQueue, UserRequestModel userRequestModel);
}
