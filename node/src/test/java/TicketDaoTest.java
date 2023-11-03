import MTCompany.NodeApplication;
import MTCompany.dao.TicketDao;
import MTCompany.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


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
        LocalDateTime localDepartureDateTime = LocalDateTime.of(2023, 10, 30, 19, 25);

        // Выполняем тестовый запрос
        List<Ticket> foundTickets = ticketDao.findAndReturnByOriginCityNameAndDestinationCityNameAndLocalDepartureDateTime(originCityName, destinationCityName, localDepartureDateTime);

        // Проверяем результат
        if (foundTickets.isEmpty()) {
            System.out.println("Строка не найдена в базе данных");
        } else {
            System.out.println("Строка найдена в базе данных");
        }
    }
}



