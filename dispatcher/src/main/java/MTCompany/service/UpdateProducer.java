package MTCompany.service;

import MTCompany.request.model.UserRequestModel;


public interface UpdateProducer {
    void produce(String rabbitQueue, UserRequestModel userRequestModel);
}
