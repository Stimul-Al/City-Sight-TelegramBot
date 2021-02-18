package by.home.telegrambot.service;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.City;
import by.home.telegrambot.model.Sight;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.CityRepository;
import by.home.telegrambot.repository.SightRepository;
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
import static by.home.telegrambot.util.TelegramUtil.*;
import static by.home.telegrambot.util.TelegramUtil.createInlineKeyboardButton;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelService {

    private Sight sight;

    @Setter
    private static User user;

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final SightRepository sightRepository;


    public List<PartialBotApiMethod<? extends Serializable>> endEvent() {
        user.setState(State.START);
        userRepository.save(user);

        return List.of(createMessageTemplate(user));
    }

    public List<PartialBotApiMethod<? extends Serializable>> startTravel() {
        String text = "Выбирай как хочешь - случайный город, или ты хочешь какой-то свой?";
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Случайный город", NEW_CITY),
                        createInlineKeyboardButton("Я выберу свой", NEW_CITY),
                        createInlineKeyboardButton("Спасибо!", END_TRAVEL))));

        return List.of(sendMessage);
    }

    public List<PartialBotApiMethod<? extends Serializable>> getDescriptionForSight() {
        String text = sight.getDescription();
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Новая достопримечательность", NEW_SIGHT),
                        createInlineKeyboardButton("Новый город", NEW_CITY),
                        createInlineKeyboardButton("Спасибо!", END_TRAVEL))));

        return List.of(sendMessage);
    }

    public List<PartialBotApiMethod<? extends Serializable>> getNewSight() {
        sight = sightRepository.getRandomSight();

        String text = String.format("Как тебе: *%s* ?", sight.getSight());
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Новая достопримечательность", NEW_SIGHT),
                        createInlineKeyboardButton("Новый город", NEW_CITY),
                        createInlineKeyboardButton("Расскажи мне больше", CHECK_DESCRIPTION))));

        return List.of(sendMessage);
    }

    public List<PartialBotApiMethod<? extends Serializable>> getNewCity() {
        City city = cityRepository.getByRandomCity();

        String text = String.format("Город специально для тебя: %s", city.getCity());
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Новая достопримечательность", NEW_SIGHT),
                        createInlineKeyboardButton("Новый город", NEW_CITY),
                        createInlineKeyboardButton("Спасибо!", END_TRAVEL))));

        return List.of(sendMessage);
    }
}
