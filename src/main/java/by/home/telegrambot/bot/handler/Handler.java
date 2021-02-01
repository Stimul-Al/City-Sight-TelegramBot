package by.home.telegrambot.bot.handler;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

public interface Handler {

    List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message);

    State operatedBotState();

    List<String> operatedCallBackQuery();
}
