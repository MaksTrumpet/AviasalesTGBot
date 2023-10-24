package MTCompany.controller;

import MTCompany.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Component
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;

    public UpdateController(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
        }
        if(update.hasMessage()){
            distributeMessagesByType(update);
        }
    }

    private void distributeMessagesByType(Update update) {

    }
}
