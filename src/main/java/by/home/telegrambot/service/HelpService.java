package by.home.telegrambot.service;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.City;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.CityRepository;
import by.home.telegrambot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.*;
import static by.home.telegrambot.util.TelegramFunctionsUtil.CREATE_CITY;
import static by.home.telegrambot.util.TelegramUtil.*;
import static by.home.telegrambot.util.TelegramUtil.createInlineKeyboardButton;

@Service
@Slf4j
@RequiredArgsConstructor
public class HelpService {

    @Setter
    private static User user;

    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public List<PartialBotApiMethod<? extends Serializable>> createCity() {
        return null;
    }

    public List<PartialBotApiMethod<? extends Serializable>> getListCities() {
        List<City> cities = cityRepository.findAll();
        SendMessage sendMessage = createMessageTemplate(user, cities.toString(),
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Назад", USER_HELP),
                        createInlineKeyboardButton("Меню путешествий", TRAVEL_START))));

        return List.of(sendMessage);
    }

    public List<PartialBotApiMethod<? extends Serializable>> userNameChange() {
        user.setState(State.ENTER_NAME);
        userRepository.save(user);

        String text = String.format("Ваше настоящее имя *%s*%nВведите новое имя или нажмите кнопку продолжить", user.getName());
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Отмена", NAME_CHANGE_CANCEL))));

        return List.of(sendMessage);
    }

    public List<PartialBotApiMethod<? extends Serializable>> menuHelp() {
        String text = "Меню помощь!";
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Изменить имя", NAME_CHANGE),
                        createInlineKeyboardButton("Список городов", LIST_CITIES),
                        createInlineKeyboardButton("Новый город", CREATE_CITY))));

        return List.of(sendMessage);
    }

}
