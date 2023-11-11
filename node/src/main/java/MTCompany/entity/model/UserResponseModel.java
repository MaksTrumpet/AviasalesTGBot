package MTCompany.entity.model;


import MTCompany.entity.Ticket;
import lombok.*;
import lombok.extern.log4j.Log4j;

import java.time.LocalDate;
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

    private String id;
    private String duration;
    private String flight_id;
    private String carrier_code;
    private String flight_number;
    private String is_direct_flight;
    private String is_charter_flight;
    private String origin;
    private String destination;
    private LocalDateTime local_departure_at;
    private LocalDateTime local_arrival_at;
    private String origin_city;
    private String origin_iata;
    private String origin_name;
    private String origin_country_code;
    private String origin_country_name;
    private String destination_city;
    private String destination_iata;
    private String destination_name;
    private String destination_country_code;
    private String destination_country_name;
    private String carrier_name;
    private String origin_metropolis;
    private String origin_metropolis_iata;
    private LocalDate departure_date;
    private LocalDateTime created_at;

    public static List<UserResponseModel> createModelResponseForOneDayOneCityRequest(List<Ticket> tickets, Long chatId) {
        List<UserResponseModel> responseModelList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            UserResponseModel userResponseModel = UserResponseModel.builder()
                    .chatId(chatId)
                    .id(ticket.getId())
                    .duration(ticket.getDuration())
                    .flight_id(ticket.getFlight_id())
                    .carrier_code(ticket.getCarrier_code())
                    .flight_number(ticket.getFlight_number())
                    .is_direct_flight(ticket.getIs_direct_flight())
                    .is_charter_flight(ticket.getIs_charter_flight())
                    .origin(ticket.getOrigin())
                    .destination(ticket.getDestination())
                    .local_departure_at(ticket.getLocalDepartureAt())
                    .local_arrival_at(ticket.getLocal_arrival_at())
                    .origin_city(ticket.getOrigin_city())
                    .origin_iata(ticket.getOriginIata())
                    .origin_name(ticket.getOrigin_name())
                    .origin_country_code(ticket.getOrigin_country_code())
                    .origin_country_name(ticket.getOrigin_country_name())
                    .destination_city(ticket.getDestination_city())
                    .destination_iata(ticket.getDestinationIata())
                    .destination_name(ticket.getDestination_name())
                    .destination_country_code(ticket.getDestination_country_code())
                    .destination_country_name(ticket.getDestination_country_name())
                    .carrier_name(ticket.getCarrier_name())
                    .origin_metropolis(ticket.getOrigin_metropolis())
                    .origin_metropolis_iata(ticket.getOrigin_metropolis_iata())
                    .departure_date(ticket.getDepartureDate())
                    .created_at(ticket.getCreated_at())
                    .build();

            responseModelList.add(userResponseModel);
            log.debug("Model is created");
        }
        return responseModelList;
    }
}
