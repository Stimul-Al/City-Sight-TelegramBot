package by.home.telegrambot.service;

import by.home.telegrambot.model.User;
import by.home.telegrambot.util.TelegramFunctionsUtil;
import by.home.telegrambot.util.TelegramUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.TRAVEL_START;
import static by.home.telegrambot.util.TelegramUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StopService {

    @Setter
    private static User user;

    public List<PartialBotApiMethod<? extends Serializable>> endTravel() {
        String text = "До скорых встреч, мой друг! *%n Ты всегда можешь начать путешествие снова!";

        SendPhoto sendPhoto = createPhotoTemplate(user, text,
                createInlineKeyboardMarkup(List.of(
                        createInlineKeyboardButton("Начать путешествие", TRAVEL_START))));

        return List.of(sendPhoto);
    }
}