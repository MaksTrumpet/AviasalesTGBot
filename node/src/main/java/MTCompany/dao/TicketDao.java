package MTCompany.dao;


import MTCompany.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketDao extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.originCityName = :originCityName AND t.destinationCityName = :destinationCityName AND t.localDepartureDateTime BETWEEN :startOfDay AND :endOfDay")
    List<Ticket> findAndReturnByOriginCityNameAndDestinationCityNameAndLocalDepartureDate(
            @Param("originCityName") String originCityName,
            @Param("destinationCityName") String destinationCityName,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
