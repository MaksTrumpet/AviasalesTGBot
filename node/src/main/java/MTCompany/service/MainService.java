package MTCompany.service;

import MTCompany.dao.UserRequestDataDao;
import MTCompany.entity.UserRequestData;

import MTCompany.entity.UserRequestModel;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class MainService {
    private final UserRequestDataDao userRequestDataDao;

    public MainService(UserRequestDataDao userRequestDataDao) {
        this.userRequestDataDao = userRequestDataDao;
    }

    public void processOneDayOneCity(UserRequestModel userRequestModel) {
        saveUserRequestData(userRequestModel);
        log.debug("UserRequestModel saved in DB");
    }

    private void saveUserRequestData(UserRequestModel userRequestModel) {
        UserRequestData userRequestData = UserRequestData.builder()
                .chatId(userRequestModel.getChatId())
                .departureCity(userRequestModel.getDepartureCity())
                .arrivalCity(userRequestModel.getArrivalCity())
                .departureDate(userRequestModel.getDepartureDate())
                .build();
        userRequestDataDao.save(userRequestData);
    }
}
