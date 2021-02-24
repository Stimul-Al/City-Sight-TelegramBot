package by.home.telegrambot.bot.handler.impl;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.bot.handler.Handler;
import by.home.telegrambot.model.City;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.CityRepository;
import by.home.telegrambot.repository.UserRepository;
import by.home.telegrambot.service.HelpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.*;

@Component
@RequiredArgsConstructor
public class HelpHandler implements Handler {

    private final HelpService helpService;
    private final UserRepository userRepository;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        HelpService.setUser(user);
        user.setState(State.HELP);
        userRepository.save(user);

        if (message.startsWith(NAME_CHANGE)) {
            return helpService.userNameChange();
        } else if (message.startsWith(LIST_CITIES)) {
            return helpService.getListCities();
        }
        return helpService.menuHelp();
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
