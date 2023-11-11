package MTCompany.model;

import lombok.*;
import lombok.extern.log4j.Log4j;

import java.time.LocalDate;
import java.time.LocalDateTime;


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


    public String createAnswer() {
        return "Информация о билете:\n" +
//                "ID чата: " + chatId + "\n" +
//                "ID: " + id + "\n" +
                "Продолжительность: " + duration + "\n" +
//                "ID рейса: " + flight_id + "\n" +
//                "Код перевозчика: " + carrier_code + "\n" +
                "Номер рейса: " + flight_number + "\n" +
                "Прямой рейс: " + is_direct_flight + "\n" +
                "Чартерный рейс: " + is_charter_flight + "\n" +
                "Место отправления: " + origin + "\n" +
                "Место назначения: " + destination + "\n" +
                "Местное время отправления: " + local_departure_at + "\n" +
                "Местное время прибытия: " + local_arrival_at + "\n" +
                "Город отправления: " + origin_city + "\n" +
//                "IATA код города отправления: " + origin_iata + "\n" +
                "Название места отправления: " + origin_name + "\n" +
//                "Код страны отправления: " + origin_country_code + "\n" +
                "Название страны отправления: " + origin_country_name + "\n" +
                "Город назначения: " + destination_city + "\n" +
//                "IATA код города назначения: " + destination_iata + "\n" +
                "Название места назначения: " + destination_name + "\n" +
//                "Код страны назначения: " + destination_country_code + "\n" +
                "Название страны назначения: " + destination_country_name + "\n" +
                "Название перевозчика: " + carrier_name + "\n" +
                "Метрополис отправления: " + origin_metropolis + "\n" +
//                "IATA код метрополиса отправления: " + origin_metropolis_iata + "\n" +
                "Дата отправления: " + departure_date + "\n"
//                +
//                "Дата создания: " + created_at
                ;
    }

}
