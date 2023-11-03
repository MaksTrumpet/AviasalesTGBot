package MTCompany.dao;


import MTCompany.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketDao extends JpaRepository<Ticket, Long> {
    List<Ticket> findAndReturnByOriginCityNameAndDestinationCityNameAndLocalDepartureDateTime(String originCity, String destinationCity, LocalDateTime localDepartureDateTime);
}
