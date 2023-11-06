package MTCompany.entity.model;


import MTCompany.entity.Ticket;
import lombok.*;
import lombok.extern.log4j.Log4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static List<UserResponseModel> createModelResponseForOneDayOneCityRequest(List<Ticket> tickets, Long chatId) {
        List<UserResponseModel> responseModelList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            UserResponseModel userResponseModel = UserResponseModel.builder()
                    .chatId(chatId)

                    .ticketId(ticket.getTicketId())
                    .proposalId(ticket.getProposalId())
                    .pricePerPersonValue(ticket.getPricePerPersonValue())
                    .airlineId(ticket.getAirlineId())
                    .flightNumber(ticket.getFlightNumber())
                    .handbags(ticket.getHandbags())
                    .baggage(ticket.getBaggage())
                    .originAirport(ticket.getOriginAirport())
                    .destinationAirport(ticket.getDestinationAirport())
                    .localDepartureDateTime(ticket.getLocalDepartureDateTime())
                    .localArrivalDateTime(ticket.getLocalArrivalDateTime())
                    .originAirportName(ticket.getOriginAirportName())
                    .originCity(ticket.getOriginCity())
                    .originCityName(ticket.getOriginCityName())
                    .originCountry(ticket.getOriginCountry())
                    .originCountryName(ticket.getOriginCountryName())
                    .destinationAirportName(ticket.getDestinationAirportName())
                    .destinationCity(ticket.getDestinationCity())
                    .destinationCityName(ticket.getDestinationCityName())
                    .destinationCountry(ticket.getDestinationCountry())
                    .destinationCountryName(ticket.getDestinationCountryName())
                    .airlineName(ticket.getAirlineName())
                    .mainOrigin(ticket.getMainOrigin())
                    .mainDestination(ticket.getMainDestination())
                    .date(ticket.getDate())
                    .parsedAt(ticket.getParsedAt())
                    .build();

            responseModelList.add(userResponseModel);
            log.debug("Model is create");
        }
        return responseModelList;
    }
}
