package by.home.telegrambot.service;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateService {

    @Setter
    private static User user;

    private final UserRepository userRepository;

    public List<PartialBotApiMethod<? extends Serializable>> createCity() {
        SendMessage createCityMessage = createMessageTemplate(String.valueOf(user.getChatUserId()));
        createCityMessage.setText("Введите название города.");

        return List.of(createCityMessage);
    }

    public List<PartialBotApiMethod<? extends Serializable>> saveCity() {
        return null;
    }

    public List<PartialBotApiMethod<? extends Serializable>> createSight() {
        return null;
    }

    public List<PartialBotApiMethod<? extends Serializable>> saveSight() {
        return null;
    }

    public List<PartialBotApiMethod<? extends Serializable>> addPhoto() {
        return null;
    }

    public List<PartialBotApiMethod<? extends Serializable>> savePhoto() {
        return null;
    }
}
