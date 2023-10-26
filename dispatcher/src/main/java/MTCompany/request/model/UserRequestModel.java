package MTCompany.request.model;

import MTCompany.request.RequestCommands;
import MTCompany.request.RequestState;
import lombok.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@Builder
public class UserRequestModel {
    private Long chatId;

    @NotNull(message = "Departure city must not be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Invalid departure city")
    private String departureCity;

    @NotNull(message = "Arrival city must not be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Invalid arrival city")
    private String arrivalCity;

    @NotNull(message = "Date must not be null")
    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDate date;

    private RequestState requestState;
    private RequestCommands requestCommands;

    public String validate() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserRequestModel>> violations = validator.validate(this);

        if (violations.isEmpty()) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserRequestModel> violation : violations) {
                String fieldName = violation.getPropertyPath().toString();
                sb.append(violation.getMessage()).append("\n");
                cleanWithNoValidate(fieldName);
            }
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
                this.date = null;
                break;
        }
    }
}

