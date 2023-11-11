package MTCompany.request.handler.Impl;

import MTCompany.request.handler.RequestStrategy;
import MTCompany.model.UserRequestModel;

public class ArrivalCityStrategy implements RequestStrategy {
    @Override
    public String addData(UserRequestModel userRequestModel, String userMessage) {
        userRequestModel.setArrivalCity(userMessage);
        String output = userRequestModel.validate("arrivalCity");
        if (output != null) {
            output += "Введите город прибытия:";
        }
        return output;
    }
}
