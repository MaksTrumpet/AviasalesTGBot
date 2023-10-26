package MTCompany.request;

import MTCompany.request.model.UserRequestModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
@Component
public class UsersMap {
    private ConcurrentHashMap<Long, UserRequestModel> usersRequestModelConcurrentHashMap = new ConcurrentHashMap<>();
    private UserRequestModel userRequestModel;
}
