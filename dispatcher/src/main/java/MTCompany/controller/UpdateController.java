package MTCompany.controller;

import MTCompany.request.RequestCommands;
import MTCompany.request.RequestState;
import MTCompany.request.UsersMap;
import MTCompany.request.model.UserRequestModel;
import MTCompany.service.UpdateProducer;
import MTCompany.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static MTCompany.model.RabbitQueue.ONE_DATE_ONE_CITY_REQUEST;
import static MTCompany.request.RequestCommands.*;
import static MTCompany.request.RequestState.EMPTY;
import static MTCompany.request.RequestState.IN_PROCESS;

@Log4j
@Component
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;
    private final UsersMap usersMap;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer, UsersMap usersMap) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
        this.usersMap = usersMap;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update.hasMessage()) {
            var chatId = update.getMessage().getChatId();
            distributeMessagesByCommands(chatId, update);
        } else {
            log.error("unsupported message type  is received: " + update);

        }
    }

    private void distributeMessagesByCommands(Long chatId, Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    createUserRequest(START, chatId, update);
                    return;
                case "/info":
                    createUserRequest(INFO, chatId, update);
                    return;
                case "/1":
                    createUserRequest(SEARCH_TICKETS_ONE_DAY_ONE_CITY, chatId, update);
            }
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void createUserRequest(RequestCommands requestCommands, Long chatId, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        switch (requestCommands) {
            case START:
                sendMessage.setText("Приветствую! Чтобы посмотреть список параметров поиска введите:/info");
                break;
            case SEARCH_TICKETS_ONE_DAY_ONE_CITY:
                UserRequestModel userRequestModel = checkUserState(chatId);

                sendMessage.setText(searchTicketsOneDayOneCity(chatId, update, requestCommands));
                break;
            case CANCEL:
                sendMessage.setText(cancelUserSession(chatId));
                break;
            case INFO:
                sendMessage.setText(info());
                break;
        }

        setView(sendMessage);

    }

    private UserRequestModel checkUserState(Long chatId) {
        UserRequestModel userRequestModel;
        if (usersMap.getUsersRequestMap().get(chatId) == null) {
            userRequestModel = UserRequestModel.builder()
                    .chatId(chatId)
                    .requestState(IN_PROCESS)
                    .build();
            usersMap.getUsersRequestMap().put(userRequestModel.getChatId(), userRequestModel);
        }

    }

    private String cancelUserSession(Long chatId) {
        if (usersMap.getUsersRequestMap().get(chatId) != null) {
            usersMap.getUsersRequestMap().remove(chatId);
        }
        return "Сессия обновлена\n" + info();
    }


    private String searchTicketsOneDayOneCity(Long chatId, Update update, RequestCommands requestCommands) {
        String userMessage = update.getMessage().getText();
        String output = "";
        UserRequestModel userRequestModel = usersMap.getUsersRequestMap().get(chatId);
        userRequestModel.setRequestCommands(requestCommands);

        if (userRequestModel.getDepartureCity() == null) {
            if ((output = userRequestModel.validate()) == null) {
                userRequestModel.setDepartureCity(userMessage);
                output = "Введите город прибытия.";
            }

        } else if (userRequestModel.getArrivalCity() == null) {
            if ((output = userRequestModel.validate()) == null) {
                userRequestModel.setArrivalCity(userMessage);
                output = "Введите дату прибытия.";
            }
        }
        return output;
    }

    private String info() {
        return String.format("Список доступных команд:\n" +
                "%s - Поиск билета с параметрами:\n" +
                "\"город - город, дата\"  ", "/1");
    }

    private void produceProcess(RequestCommands requestCommands, Update update) {
        switch (requestCommands) {
            case SEARCH_TICKETS_ONE_DAY_ONE_CITY:
                updateProducer.produce(ONE_DATE_ONE_CITY_REQUEST, update);
                log.info("Update produce to rabbit");
                return;
        }
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    private void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

}
