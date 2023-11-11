package MTCompany.utils;

import MTCompany.model.UserRequestModel;
import lombok.extern.log4j.Log4j;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;

@Log4j
public class ValidationRequestImpl implements ValidationRequest {
    private static final CityIATALoader cityIATA = new CityIATALoader();

    public String validateRequest(String fieldToValidate, UserRequestModel userRequestModel) {
        Validator validator = buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserRequestModel>> violations;
        StringBuilder output;

        switch (fieldToValidate) {
            case "departureCity":
                violations = validator.validateProperty(userRequestModel, "departureCity");
                break;
            case "arrivalCity":
                violations = validator.validateProperty(userRequestModel, "arrivalCity");
                break;
            case "departureDate":
                violations = validator.validateProperty(userRequestModel, "departureDate");
                break;
            default:
                log.error("Unexpected value: " + fieldToValidate);
                return "Ошибка при валидации поля: " + fieldToValidate;
        }

        if (!violations.isEmpty()) {
            output = new StringBuilder();
            for (ConstraintViolation<UserRequestModel> violation : violations) {
                output.append(violation.getMessage()).append("\n");
            }
        } else {
            if (fieldToValidate.equals("departureDate")) return null;
            if (cityIATA.convertCityToIATA(userRequestModel, fieldToValidate)) return null;
            else {
                output = new StringBuilder();
                output.append("Некорректное название города\n");
            }
        }
        cleanWithNoValidate(fieldToValidate, userRequestModel);

        return output.toString();
    }


    public void cleanWithNoValidate(String fieldName, UserRequestModel userRequestModel) {
        switch (fieldName) {
            case "departureCity":
                userRequestModel.setDepartureCity(null);
                break;
            case "arrivalCity":
                userRequestModel.setArrivalCity(null);
                break;
            case "departureDate":
                userRequestModel.setDepartureDate(null);
                break;
        }
    }
}
