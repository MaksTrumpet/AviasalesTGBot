package MTCompany.model;

import lombok.*;
import lombok.extern.log4j.Log4j;

import java.time.LocalDateTime;


@Log4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel {
    private Long chatId;

    private String ticketId;
    private String proposalId;
    private Double pricePerPersonValue;
    private String airlineId;
    private Integer flightNumber;
    private Integer handbags;
    private Integer baggage;
    private String originAirport;
    private String destinationAirport;
    private LocalDateTime localDepartureDateTime;
    private LocalDateTime localArrivalDateTime;
    private String originAirportName;
    private String originCity;
    private String originCityName;
    private String originCountry;
    private String originCountryName;
    private String destinationAirportName;
    private String destinationCity;
    private String destinationCityName;
    private String destinationCountry;
    private String destinationCountryName;
    private String airlineName;
    private String mainOrigin;
    private String mainDestination;
    private LocalDateTime date;
    private LocalDateTime parsedAt;

    public String createAnswer() {
        return "Идентификатор билета: " + ticketId + "\n" +
                "Идентификатор предложения: " + proposalId + "\n" +
                "Цена за человека: " + pricePerPersonValue + "\n" +
                "Идентификатор авиакомпании: " + airlineId + "\n" +
                "Номер рейса: " + flightNumber + "\n" +
                "Количество ручной клади: " + handbags + "\n" +
                "Количество багажа: " + baggage + "\n" +
                "Аэропорт отправления: " + originAirport + "\n" +
                "Аэропорт назначения: " + destinationAirport + "\n" +
                "Местное время отправления: " + localDepartureDateTime + "\n" +
                "Местное время прибытия: " + localArrivalDateTime + "\n" +
                "Название аэропорта отправления: " + originAirportName + "\n" +
                "Город отправления: " + originCity + "\n" +
                "Название города отправления: " + originCityName + "\n" +
                "Страна отправления: " + originCountry + "\n" +
                "Название страны отправления: " + originCountryName + "\n" +
                "Название аэропорта назначения: " + destinationAirportName + "\n" +
                "Город назначения: " + destinationCity + "\n" +
                "Название города назначения: " + destinationCityName + "\n" +
                "Страна назначения: " + destinationCountry + "\n" +
                "Название страны назначения: " + destinationCountryName + "\n" +
                "Название авиакомпании: " + airlineName + "\n" +
                "Основное место отправления: " + mainOrigin + "\n" +
                "Основное место назначения: " + mainDestination + "\n" +
                "Дата: " + date + "\n" +
                "Время разбора: " + parsedAt;
    }

}
