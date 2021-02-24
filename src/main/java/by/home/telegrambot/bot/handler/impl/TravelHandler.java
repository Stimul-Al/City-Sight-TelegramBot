package by.home.telegrambot.bot.handler.impl;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.bot.handler.Handler;
import by.home.telegrambot.model.User;
import by.home.telegrambot.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.*;

@Component
@RequiredArgsConstructor
public class TravelHandler implements Handler {

    private final TravelService travelService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        TravelService.setUser(user);

        if (message.startsWith(NEW_CITY)) {
            return travelService.getNewCity();
        } else if (message.startsWith(NEW_SIGHT)) {
            return travelService.getNewSight();
        } else if (message.startsWith(CHECK_DESCRIPTION)) {
            return travelService.getDescriptionForSight();
        } else if (message.startsWith(END_TRAVEL)) {
            return travelService.endEvent();
        }
        return travelService.startTravel();
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
