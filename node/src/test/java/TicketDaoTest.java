import MTCompany.NodeApplication;
import MTCompany.dao.TicketDao;
import MTCompany.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = NodeApplication.class)
public class TicketDaoTest {

    @Autowired
    private TicketDao ticketDao;

    @Test
    public void testIfRowExistsInDatabase() {
        // Запрашиваемые данные
        String originCityName = "Сочи";
        String destinationCityName = "Москва";
        LocalDate localDepartureDate = LocalDate.of(2023, 12, 30);
        LocalDateTime startOfDay = localDepartureDate.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Ticket> foundTickets = ticketDao.findAndReturnByOriginCityNameAndDestinationCityNameAndLocalDepartureDate(originCityName, destinationCityName, startOfDay, endOfDay);

        // Проверяем результат
        if (foundTickets.isEmpty()) {
            System.out.println("Строка не найдена в базе данных");
        } else {
            System.out.println("Строка найдена в базе данных");
        }
    }
}
