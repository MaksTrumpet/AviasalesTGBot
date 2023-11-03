package MTCompany.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", proposalId=" + proposalId +
                ", pricePerPersonValue=" + pricePerPersonValue +
                ", airlineId=" + airlineId +
                ", flightNumber='" + flightNumber + '\'' +
                ", handbags=" + handbags +
                ", baggage=" + baggage +
                ", originAirport='" + originAirport + '\'' +
                ", destinationAirport='" + destinationAirport + '\'' +
                ", localDepartureDateTime=" + localDepartureDateTime +
                ", localArrivalDateTime=" + localArrivalDateTime +
                ", originAirportName='" + originAirportName + '\'' +
                ", originCity='" + originCity + '\'' +
                ", originCityName='" + originCityName + '\'' +
                ", originCountry='" + originCountry + '\'' +
                ", originCountryName='" + originCountryName + '\'' +
                ", destinationAirportName='" + destinationAirportName + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationCityName='" + destinationCityName + '\'' +
                ", destinationCountry='" + destinationCountry + '\'' +
                ", destinationCountryName='" + destinationCountryName + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", mainOrigin='" + mainOrigin + '\'' +
                ", mainDestination='" + mainDestination + '\'' +
                ", date=" + date +
                ", parsedAt=" + parsedAt +
                '}';
    }
}

