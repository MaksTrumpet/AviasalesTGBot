package MTCompany.request.handler;

import MTCompany.model.UserRequestModel;

public interface RequestHandler {
    String addData(UserRequestModel userRequestModel, String userMessage);
}
