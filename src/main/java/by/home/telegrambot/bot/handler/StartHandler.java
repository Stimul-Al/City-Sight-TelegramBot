package by.home.telegrambot.bot.handler;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.UserRepository;
import by.home.telegrambot.util.TelegramUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.*;
import static by.home.telegrambot.util.TelegramUtil.*;

@Component
public class StartHandler implements Handler {

    @Value("${bot.name}")
    private String botUsername;

    private final UserRepository userRepository;

    public StartHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        SendMessage welcomeMessage = createMessageTemplate(user);
        welcomeMessage.setText(String.format("Привет, %s! Я бот *%s*%n" +
                "Я могу помочь тебе c выбором посещения достопримечательности!", user.getName(), botUsername));

        String text = String.format("Тебя зовут %s?", user.getName());
        SendMessage registrationMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Да", NAME_ACCEPT),
                        createInlineKeyboardButton("Нет (изменить имя)", NAME_CHANGE))));

        user.setState(State.ENTER_NAME);
        userRepository.save(user);

        return List.of(welcomeMessage, registrationMessage);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
