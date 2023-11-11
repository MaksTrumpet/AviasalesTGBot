package MTCompany.entity.model;


import lombok.*;


import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel implements Comparable<UserRequestModel> {
    private Long chatId;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private int is_monitoring;
    private boolean highPriority;

    @Override
    public int compareTo(UserRequestModel o) {
        if (this.highPriority && !o.highPriority) {
            return -1;
        } else {
            return 1;
        }
    }
}




