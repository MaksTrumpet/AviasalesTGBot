package MTCompany.entity.model;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel {
    private String ticketId;
    private Long chatId;
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
}
