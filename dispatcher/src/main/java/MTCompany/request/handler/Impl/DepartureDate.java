package MTCompany.request.handler.Impl;

import MTCompany.request.handler.RequestHandler;
import MTCompany.model.UserRequestModel;

import java.time.LocalDate;

public class DepartureDate implements RequestHandler {
    @Override
    public String addData(UserRequestModel userRequestModel, String userMessage) {

        userRequestModel.setDepartureDate(LocalDate.parse(userMessage));
        String output = userRequestModel.validate("departureDate");
        if (output != null) {
            output += "Введите дату отправления:";
        }
        return output;
    }
}
