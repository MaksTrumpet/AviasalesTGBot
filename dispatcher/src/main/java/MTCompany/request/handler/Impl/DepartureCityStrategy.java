package MTCompany.request.handler.Impl;

import MTCompany.request.handler.RequestStrategy;
import MTCompany.model.UserRequestModel;

public class DepartureCityStrategy implements RequestStrategy {
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
