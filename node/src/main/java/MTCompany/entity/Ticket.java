package MTCompany.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "duration")
    private String duration;

    @Column(name = "flight_id")
    private String flight_id;

    @Column(name = "carrier_code")
    private String carrier_code;

    @Column(name = "flight_number")
    private String flight_number;

    @Column(name = "is_direct_flight")
    private String is_direct_flight;

    @Column(name = "is_charter_flight")
    private String is_charter_flight;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "localDepartureAt")
    private LocalDateTime localDepartureAt;

    @Column(name = "local_arrival_at")
    private LocalDateTime local_arrival_at;

    @Column(name = "origin_city")
    private String origin_city;

    @Column(name = "originIata")
    private String originIata;

    @Column(name = "origin_name")
    private String origin_name;

    @Column(name = "origin_country_code")
    private String origin_country_code;

    @Column(name = "origin_country_name")
    private String origin_country_name;

    @Column(name = "destination_city")
    private String destination_city;

    @Column(name = "destinationIata")
    private String destinationIata;

    @Column(name = "destination_name")
    private String destination_name;

    @Column(name = "destination_country_code")
    private String destination_country_code;

    @Column(name = "destination_country_name")
    private String destination_country_name;

    @Column(name = "carrier_name")
    private String carrier_name;

    @Column(name = "origin_metropolis")
    private String origin_metropolis;

    @Column(name = "origin_metropolis_iata")
    private String origin_metropolis_iata;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "created_at")
    private LocalDateTime created_at;
}


