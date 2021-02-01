package by.home.telegrambot.bot.handler;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.*;
import static by.home.telegrambot.util.TelegramUtil.*;

@Component
public class RegistrationHandler implements Handler {

    private final UserRepository userRepository;

    public RegistrationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {

        if (message.equalsIgnoreCase(NAME_ACCEPT) || message.equalsIgnoreCase(NAME_CHANGE_CANCEL)) {
            return accept(user);
        } else if (message.equalsIgnoreCase(NAME_CHANGE)) {
            return changeName(user);
        }
        return checkName(user, message);
    }

    private List<PartialBotApiMethod<? extends Serializable>> accept(User user) {
        user.setState(State.CHOOSE_CITY);
        userRepository.save(user);

        String text = String.format("Ну что, %s, начнем путешествие?", user.getName());
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Начало путешествия", TRAVEL_START),
                        createInlineKeyboardButton("Помощь", USER_HELP))));

        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> checkName(User user, String message) {
        user.setName(message);
        userRepository.save(user);

        String text = String.format("Вы ввели: %s%nЕсли все верно нажмите кнопку принять", user.getName());
        SendMessage sendMessage = createMessageTemplate(user, text,
                getCurrentEventButton("Принять", NAME_ACCEPT));

        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> changeName(User user) {
        user.setState(State.ENTER_NAME);
        userRepository.save(user);

        String text = String.format("Ваше настоящее имя *%s*%nВведите новое имя или нажмите кнопку продолжить", user.getName());
        SendMessage sendMessage = createMessageTemplate(user, text,
                getCurrentEventButton("Отмена", NAME_CHANGE_CANCEL));

        return List.of(sendMessage);
    }

    private InlineKeyboardMarkup getCurrentEventButton(String text, String command) {
        return createInlineKeyboardMarkup(List.of(
                createInlineKeyboardButton(text, command)));
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
