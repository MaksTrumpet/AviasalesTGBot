package MTCompany.request;

import MTCompany.model.UserRequestModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static MTCompany.request.RequestState.IN_PROCESS;

@Setter
@Getter
@Component
public class UsersMap {
    private ConcurrentHashMap<Long, UserRequestModel> usersRequestMap = new ConcurrentHashMap<>();
    private UserRequestModel userRequestModel;

    public boolean addUserToMap(Long chatId, String userCommand) {
        RequestCommands requestCommand = RequestCommands.fromString(userCommand);
        if (requestCommand == null) {
            return false;
        }

        UserRequestModel userRequestModel;
        userRequestModel = UserRequestModel.builder()
                .chatId(chatId)
                .requestState(IN_PROCESS)
                .requestCommands(requestCommand)
                .build();
        getUsersRequestMap().put(userRequestModel.getChatId(), userRequestModel);
        return true;

    }
}
