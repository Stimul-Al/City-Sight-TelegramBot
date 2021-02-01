package by.home.telegrambot.bot.handler;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.City;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.CityRepository;
import by.home.telegrambot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.*;
import static by.home.telegrambot.util.TelegramUtil.*;

@Component
@RequiredArgsConstructor
public class HelpHandler implements Handler {

    private User user;

    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        this.user = user;
        this.user.setState(State.HELP);
        userRepository.save(user);

        if (message.startsWith(NAME_CHANGE)) {
            return userNameChange();
        } else if (message.startsWith(LIST_CITIES)) {
            return getListCities();
        } else if (message.startsWith(CREATE_CITY)) {
            return createCity();
        }
        return menuHelp();
    }

    private List<PartialBotApiMethod<? extends Serializable>> createCity() {
        return null;
    }

    private List<PartialBotApiMethod<? extends Serializable>> getListCities() {
        List<City> cities = cityRepository.findAll();
        SendMessage sendMessage = createMessageTemplate(user, cities.toString(),
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Назад", USER_HELP),
                        createInlineKeyboardButton("Меню путешествий", TRAVEL_START))));

        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> userNameChange() {
        user.setState(State.ENTER_NAME);
        userRepository.save(user);

        String text = String.format("Ваше настоящее имя *%s*%nВведите новое имя или нажмите кнопку продолжить", user.getName());
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Отмена", NAME_CHANGE_CANCEL))));

        return List.of(sendMessage);
    }

    public List<PartialBotApiMethod<? extends Serializable>> menuHelp() {
        String text = String.format("Меню помощь!", user.getName());
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Изменить имя", NAME_CHANGE),
                        createInlineKeyboardButton("Список городов", LIST_CITIES),
                        createInlineKeyboardButton("Новый город", CREATE_CITY))));

        return List.of(sendMessage);
    }



    @Override
    public State operatedBotState() {
        return State.HELP;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(LIST_CITIES, USER_HELP, CREATE_CITY);
    }
}
