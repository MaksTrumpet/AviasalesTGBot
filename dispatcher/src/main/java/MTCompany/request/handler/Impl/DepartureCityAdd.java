package MTCompany.request.handler.Impl;

import MTCompany.request.handler.RequestHandler;
import MTCompany.request.model.UserRequestModel;

public class DepartureCityAdd implements RequestHandler {
    @Override
    public String addData(UserRequestModel userRequestModel, String userMessage) {
        userRequestModel.setDepartureCity(userMessage);
        String output = userRequestModel.validate("departureCity");
        if (output != null) {
            output += "Введите город отправления:";
        }
        return output;
    }
}
