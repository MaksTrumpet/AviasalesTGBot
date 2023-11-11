package MTCompany.request.handler.Impl;

import MTCompany.request.handler.RequestStrategy;
import MTCompany.model.UserRequestModel;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DepartureDateStrategy implements RequestStrategy {
    @Override
    public String addData(UserRequestModel userRequestModel, String userMessage) {
        LocalDate date;
        try {
            date = LocalDate.parse(userMessage);
        } catch (DateTimeParseException e) {
            return "С датой не выебывася, вводи нормально!";
        }
        userRequestModel.setDepartureDate(date);
        String output = userRequestModel.validate("departureDate");
        if (output != null) {
            output += "Введите дату отправления:";
        }
        return output;
    }
}
