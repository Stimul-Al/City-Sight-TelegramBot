package by.home.telegrambot.bot.handler.impl;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.bot.handler.Handler;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.UserRepository;
import by.home.telegrambot.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.*;

@Component
@RequiredArgsConstructor
public class RegistrationHandler implements Handler {

    private final RegistrationService registrationService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        RegistrationService.setUser(user);

        if (message.equalsIgnoreCase(NAME_ACCEPT) || message.equalsIgnoreCase(NAME_CHANGE_CANCEL)) {
            return registrationService.accept();
        } else if (message.equalsIgnoreCase(NAME_CHANGE)) {
            return registrationService.changeName();
        }
        return registrationService.checkName(message);
    }

    @Override
    public State operatedBotState() {
        return State.ENTER_NAME;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(NAME_CHANGE, NAME_ACCEPT, NAME_CHANGE_CANCEL);
    }
}
