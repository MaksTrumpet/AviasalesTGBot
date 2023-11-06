package MTCompany.controller;

import MTCompany.request.RequestCommands;
import MTCompany.request.UsersMap;

import MTCompany.model.UserRequestModel;
import MTCompany.service.UpdateProducer;
import MTCompany.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static MTCompany.model.RabbitQueue.ONE_DATE_ONE_CITY_REQUEST;
import static MTCompany.request.RequestCommands.*;
import static MTCompany.request.RequestState.READY;


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
            String userMessage = message.getText();
            switch (message.getText()) {
                case "/start":
                    createUserRequest(START, chatId, userMessage);
                    return;
                case "/info":
                    createUserRequest(INFO, chatId, userMessage);
                    return;
                case "/1":
                    createUserRequest(SEARCH_TICKETS_ONE_DAY_ONE_CITY, chatId, userMessage);
                    return;
                case "/cancel":
                    createUserRequest(CANCEL, chatId, userMessage);
                    return;
                default:
                    createUserRequest(INPUT_DATA, chatId, userMessage);
            }
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void createUserRequest(RequestCommands requestCommands, Long chatId, String userMessage) {
        UserRequestModel userRequestModel = usersMap.getUsersRequestMap().get(chatId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (userRequestModel == null) {

            if (!usersMap.addUserToMap(chatId, userMessage)) {
                sendMessage.setText("Для начала выберите одну из опций поиска.\n" + info());
                setView(sendMessage);
                return;
            } else {
                userRequestModel = usersMap.getUsersRequestMap().get(chatId);
            }
        }
        switch (requestCommands) {
            case START:
                sendMessage.setText("Приветствую!\nЧтобы посмотреть список параметров поиска введите:\n/info");
                break;
            case SEARCH_TICKETS_ONE_DAY_ONE_CITY:
                sendMessage.setText(userRequestModel.searchTicketsOneDayOneCity(userMessage));
                break;
            case CANCEL:
                sendMessage.setText(cancelUserSession(chatId));
                break;
            case INFO:
                sendMessage.setText(info());
                break;
            case INPUT_DATA:
                sendMessage.setText(checkUserCommandAndDistribute(userMessage, userRequestModel));
        }
        setView(sendMessage);
        if (userRequestModel.getRequestState() == READY) {
            produceProcess(userRequestModel);
            usersMap.getUsersRequestMap().remove(chatId);
        }
    }

    private String checkUserCommandAndDistribute(String userMessage, UserRequestModel userRequestModel) {
        String output = "";
        switch (userRequestModel.getRequestCommands()) {
            case SEARCH_TICKETS_ONE_DAY_ONE_CITY:
                output = userRequestModel.searchTicketsOneDayOneCity(userMessage);
        }
        return output;
    }


    private String cancelUserSession(Long chatId) {
        if (usersMap.getUsersRequestMap().get(chatId) != null) {
            usersMap.getUsersRequestMap().remove(chatId);
        }
        return "Сессия закрыта!\n" + info(); //TODO переименовать на (отчистить параметры поиска)
    }


    private String info() {
        return "Список доступных команд:\n" +
                SEARCH_TICKETS_ONE_DAY_ONE_CITY + " - Поиск билета с параметрами:\"город - город, дата\"\n" +
                CANCEL + " - Закрыть сессию";
    }

    private void produceProcess(UserRequestModel userRequestModel) {
        switch (userRequestModel.getRequestCommands()) {
            case SEARCH_TICKETS_ONE_DAY_ONE_CITY:
                updateProducer.produce(ONE_DATE_ONE_CITY_REQUEST, userRequestModel);
                log.info("Update produce to rabbit");
                return;
        }
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

}
