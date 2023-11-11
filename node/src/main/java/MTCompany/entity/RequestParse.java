package MTCompany.entity;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "request_parse")
public class RequestParse {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_city")
    private String departureCityIATA;

    @Column(name = "arrival_city")
    private String arrivalCityIATA;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "subscribes")
    private Integer is_monitoring;

}