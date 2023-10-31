package MTCompany.request.handler;

import MTCompany.request.model.UserRequestModel;

public interface RequestHandler {
    String addData(UserRequestModel userRequestModel, String userMessage);
}
