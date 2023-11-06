package MTCompany.model;

import MTCompany.request.RequestCommands;
import MTCompany.request.RequestState;
import MTCompany.request.handler.Impl.ArrivalCityAdd;
import MTCompany.request.handler.Impl.DepartureCityAdd;
import MTCompany.request.handler.Impl.DepartureDate;
import MTCompany.request.handler.RequestHandler;
import lombok.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.Set;

import static MTCompany.request.RequestCommands.SEARCH_TICKETS_ONE_DAY_ONE_CITY;
import static MTCompany.request.RequestState.READY;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel {
    private Long chatId;

    @NotNull(message = "Город отправления не должен быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s-]+$", message = "Некорректное название города отправления")
    private String departureCity;

    @NotNull(message = "Город прибытия не должен быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s-]+$", message = "Некорректное название города прибытия")
    private String arrivalCity;

    @NotNull(message = "Дата не должна быть пустой")
    @FutureOrPresent(message = "Дата должна быть сегодняшней или будущей")
    private LocalDate departureDate;


    private transient RequestState requestState;
    private transient RequestCommands requestCommands;


    public String searchTicketsOneDayOneCity(String userMessage) {
        if (this.getDepartureCity() == null && userMessage.equals((SEARCH_TICKETS_ONE_DAY_ONE_CITY.toString()))) {
            this.setRequestCommands(SEARCH_TICKETS_ONE_DAY_ONE_CITY);
            return "Введите город отправления:";
        }

        RequestHandler requestHandler;
        String output = "Ваш запрос обрабатывается.";

        if (this.getDepartureCity() == null) {
            requestHandler = new DepartureCityAdd();
            String departureCityAddResult = requestHandler.addData(this, userMessage);
            if (departureCityAddResult != null) {
                output = departureCityAddResult;
                return output;
            }
            return "Введите город прибытия:";
        } else if (this.getArrivalCity() == null) {
            requestHandler = new ArrivalCityAdd();
            String arrivalCityAddResult = requestHandler.addData(this, userMessage);
            if (arrivalCityAddResult != null) {
                output = arrivalCityAddResult;
                return output;
            }
            return "Введите дату отправления:";
        } else if (this.getDepartureDate() == null) {//TODO установить календарь
            requestHandler = new DepartureDate();
            String departureDateAddResult = requestHandler.addData(this, userMessage);
            if (departureDateAddResult != null) {
                output = departureDateAddResult;
            } else {
                this.setRequestState(READY);
            }

            return output;
        }
        return output;
    }


    public String validate(String fieldToValidate) {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserRequestModel>> violations;


        switch (fieldToValidate) {
            case "departureCity":
                violations = validator.validateProperty(this, "departureCity");
                break;
            case "arrivalCity":
                violations = validator.validateProperty(this, "arrivalCity");
                break;
            case "date":
                violations = validator.validateProperty(this, "departureDate");
                break;
            default:
                violations = validator.validate(this);
        }

        if (violations.isEmpty()) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserRequestModel> violation : violations) {
                String fieldName = violation.getPropertyPath().toString();
                sb.append(violation.getMessage()).append("\n");
                cleanWithNoValidate(fieldName);
            }
            violations.clear();
            return sb.toString();
        }
    }

    private void cleanWithNoValidate(String fieldName) {
        switch (fieldName) {
            case "departureCity":
                this.departureCity = null;
                break;
            case "arrivalCity":
                this.arrivalCity = null;
                break;
            case "date":
                this.departureDate = null;
                break;
        }
    }

    @Override
    public String toString() {
        return "UserRequestModel{" +
                "chatId=" + chatId +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", departureDate=" + departureDate +
                ", requestState=" + requestState +
                ", requestCommands=" + requestCommands +
                '}';
    }
}

