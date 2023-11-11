package MTCompany.request.handler;

import MTCompany.model.UserRequestModel;

public interface RequestStrategy {
    String addData(UserRequestModel userRequestModel, String userMessage);
}
