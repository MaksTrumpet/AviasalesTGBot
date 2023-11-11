package MTCompany.dao;

import MTCompany.entity.RequestParse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RequestParseRepository extends JpaRepository<RequestParse, Long> {
    Optional<RequestParse> findByDepartureCityIATAAndArrivalCityIATAAndDepartureDate(String departureCity, String arrivalCity, LocalDate departureDate);
}
