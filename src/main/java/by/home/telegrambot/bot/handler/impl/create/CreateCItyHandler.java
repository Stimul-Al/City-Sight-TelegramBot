package by.home.telegrambot.bot.handler.impl.create;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.bot.handler.Handler;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.UserRepository;
import by.home.telegrambot.service.CreateService;
import by.home.telegrambot.util.TelegramFunctionsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.*;

@Component
@RequiredArgsConstructor
public class CreateCItyHandler implements Handler {

    private final CreateService createService;
    private final UserRepository userRepository;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        CreateService.setUser(user);
        user.setState(State.CREATE);
        userRepository.save(user);

        if(message.startsWith(CREATE_CITY)) {
            return createService.createCity();
        } else if(message.startsWith(SAVE_CITY)) {
            return createService.saveCity();
        } else if(message.startsWith(CREATE_SIGHT)) {
            return createService.createSight();
        } else if(message.startsWith(SAVE_SIGHT)) {
            return createService.saveSight();
        } else if(message.startsWith(ADD_PHOTO)) {
            return createService.addPhoto();
        } else if(message.startsWith(SAVE_PHOTO)) {
            return createService.savePhoto();
        }
        return null;
    }

    @Override
    public State operatedBotState() {
        return State.CREATE;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(CREATE_CITY, SAVE_CITY, CREATE_SIGHT, SAVE_SIGHT, ADD_PHOTO, SAVE_PHOTO);
    }
}
