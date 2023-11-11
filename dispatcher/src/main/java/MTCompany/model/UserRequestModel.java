package MTCompany.model;

import MTCompany.request.RequestCommands;
import MTCompany.request.RequestState;
import MTCompany.request.handler.Impl.ArrivalCityStrategy;
import MTCompany.request.handler.Impl.DepartureCityStrategy;
import MTCompany.request.handler.Impl.DepartureDateStrategy;
import MTCompany.request.handler.RequestStrategy;
import MTCompany.utils.ValidationRequest;
import MTCompany.utils.ValidationRequestImpl;
import lombok.*;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.time.LocalDate;

import static MTCompany.request.RequestCommands.SEARCH_TICKETS_ONE_DAY_ONE_CITY;
import static MTCompany.request.RequestState.READY;


@Getter
@Setter
@Builder
public class UserRequestModel {

    private Long chatId;

    @NotNull(message = "Город отправления не должен быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s-]+$", message = "Некорректное название города")
    private String departureCity;

    @NotNull(message = "Город прибытия не должен быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s-]+$", message = "Некорректное название города")
    private String arrivalCity;

    @NotNull(message = "Дата не должна быть пустой")
    @FutureOrPresent(message = "Дата должна быть сегодняшней или будущей")
    private LocalDate departureDate;

    @Builder.Default
    private int is_monitoring = 0;
    @Builder.Default
    private boolean highPriority = false;

    private transient RequestState requestState;
    private transient RequestCommands requestCommands;


    public String searchTicketsOneDayOneCity(String userMessage) {
        if (this.getDepartureCity() == null && userMessage.equals((SEARCH_TICKETS_ONE_DAY_ONE_CITY.toString()))) {
            this.setRequestCommands(SEARCH_TICKETS_ONE_DAY_ONE_CITY);
            return "Введите город отправления:";
        }

        RequestStrategy requestStrategy;
        String output = "Ваш запрос обрабатывается.";

        if (this.getDepartureCity() == null) {
            requestStrategy = new DepartureCityStrategy();
            String departureCityAddResult = requestStrategy.addData(this, userMessage);
            if (departureCityAddResult != null) {
                output = departureCityAddResult;
                return output;
            }
            return "Введите город прибытия:";
        } else if (this.getArrivalCity() == null) {
            requestStrategy = new ArrivalCityStrategy();
            String arrivalCityAddResult = requestStrategy.addData(this, userMessage);
            if (arrivalCityAddResult != null) {
                output = arrivalCityAddResult;
                return output;
            }
            return "Введите дату отправления:";
        } else if (this.getDepartureDate() == null) {//TODO установить календарь
            requestStrategy = new DepartureDateStrategy();
            String departureDateAddResult = requestStrategy.addData(this, userMessage);
            if (departureDateAddResult != null) {
                output = departureDateAddResult;
            } else {
                this.setRequestState(READY);
            }

            return output;
        }
        return output;
    }

    public String validate(String dataForValid) {
        ValidationRequest validationRequest = new ValidationRequestImpl();
        return validationRequest.validateRequest(dataForValid, this);
    }


    @Override
    public String toString() {
        return "UserRequestModel{" +
                "chatId=" + chatId +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", departureDate=" + departureDate +
                ", is_monitoring=" + is_monitoring +
                ", highPriority=" + highPriority +
                ", requestState=" + requestState +
                ", requestCommands=" + requestCommands +
                '}';
    }
}

