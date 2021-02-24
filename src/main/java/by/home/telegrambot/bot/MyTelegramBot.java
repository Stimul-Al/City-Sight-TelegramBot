package by.home.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
public final class MyTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final UpdateReceiver updateReceiver;

    public MyTelegramBot(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messageToSend = updateReceiver.handle(update);

        if (messageToSend != null && !messageToSend.isEmpty()) {
            messageToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                } else if (response instanceof SendPhoto) {
                    executeWithExceptionCheck((SendPhoto) response);
                }
            });
        }

    }

    private void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("oops");
        }
    }

    private void executeWithExceptionCheck(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("oops");
        }
    }
}
