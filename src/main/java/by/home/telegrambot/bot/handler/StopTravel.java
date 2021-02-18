package by.home.telegrambot.bot.handler;

import by.home.telegrambot.bot.State;
import by.home.telegrambot.model.User;
import by.home.telegrambot.service.StopService;
import by.home.telegrambot.util.TelegramFunctionsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

import static by.home.telegrambot.util.TelegramFunctionsUtil.END_TRAVEL;

@Component
@RequiredArgsConstructor
public class StopTravel implements Handler {

    private final StopService stopService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        StopService.setUser(user);

        if (message.startsWith(END_TRAVEL)) {
            return stopService.endTravel();
        }
        return null;
    }

    @Override
    public State operatedBotState() {
        return State.END;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(END_TRAVEL);
    }
}
