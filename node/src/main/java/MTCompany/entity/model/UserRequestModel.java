package MTCompany.entity.model;


import lombok.*;


import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel {
    private Long chatId;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
}




