package MTCompany.service;

import MTCompany.request.model.UserRequestModel;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducer {
    void produce(String rabbitQueue, UserRequestModel userRequestModel);
}
