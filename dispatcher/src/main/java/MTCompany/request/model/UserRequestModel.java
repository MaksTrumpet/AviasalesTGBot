package MTCompany.request.model;

import MTCompany.request.RequestCommands;
import MTCompany.request.RequestState;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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
}

