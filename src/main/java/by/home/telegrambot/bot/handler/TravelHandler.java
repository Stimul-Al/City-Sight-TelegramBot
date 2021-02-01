package by.home.telegrambot.bot.handler;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.City;
import by.home.telegrambot.model.Sight;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.CityRepository;
import by.home.telegrambot.repository.SightRepository;
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
public class TravelHandler implements Handler {

    private Sight sight;
    private City city;
    private User user;

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final SightRepository sightRepository;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        this.user = user;

        if (message.startsWith(NEW_CITY)) {
            return getNewCity();
        } else if (message.startsWith(NEW_SIGHT)) {
            return getNewSight();
        } else if (message.startsWith(CHECK_DESCRIPTION)) {
            return getDescriptionForSight();
        } else if (message.startsWith(END_TRAVEL)) {
            return endEvent();
        }
        return startTravel();
    }

    private List<PartialBotApiMethod<? extends Serializable>> endEvent() {
        user.setState(State.START);
        userRepository.save(user);

        return List.of(createMessageTemplate(user));
    }

    private List<PartialBotApiMethod<? extends Serializable>> startTravel() {
        String text = "Выбирай как хочешь - случайный город, или ты хочешь какой-то свой?";
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Случайный город", NEW_CITY),
                        createInlineKeyboardButton("Я выберу свой", NEW_CITY),
                        createInlineKeyboardButton("Спасибо!", END_TRAVEL))));

        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> getDescriptionForSight() {
        String text = sight.getDescription();
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Новая достопримечательность", NEW_SIGHT),
                        createInlineKeyboardButton("Новый город", NEW_CITY),
                        createInlineKeyboardButton("Спасибо!", END_TRAVEL))));

        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> getNewSight() {
        sight = sightRepository.getRandomSight();

        String text = String.format("Как тебе: *%s* ?", sight.getSight());
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Новая достопримечательность", NEW_SIGHT),
                        createInlineKeyboardButton("Новый город", NEW_CITY),
                        createInlineKeyboardButton("Расскажи мне больше", CHECK_DESCRIPTION))));

        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> getNewCity() {
        city = cityRepository.getByRandomCity();

        String text = String.format("Город специально для тебя: %s", city.getCity());
        SendMessage sendMessage = createMessageTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Новая достопримечательность", NEW_SIGHT),
                        createInlineKeyboardButton("Новый город", NEW_CITY),
                        createInlineKeyboardButton("Спасибо!", END_TRAVEL))));

        return List.of(sendMessage);
    }

    @Override
    public State operatedBotState() {
        return State.CHOOSE_CITY;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(TRAVEL_START, NEW_CITY, NEW_SIGHT, END_TRAVEL, CHECK_DESCRIPTION);
    }
}
