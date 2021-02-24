package by.home.telegrambot.bot;

import by.home.telegrambot.bot.handler.*;
import by.home.telegrambot.model.User;
import by.home.telegrambot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class UpdateReceiver {

    private final List<Handler> handlers;
    private final UserRepository userRepository;

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        try {
            if (isMessageWithText(update)) {
                final Message message = update.getMessage();
                final User user = getUser(message);

                return getHandlerByState(user.getState()).handle(user, message.getText());

            } else if (update.hasCallbackQuery()) {
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final User user = getUser(callbackQuery);

                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData());
            }

            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            return Collections.emptyList();
        }
    }

    private User getUser(Message message) {
        final int chatId = message.getFrom().getId();
        final String name = message.getFrom().getFirstName();

        return userRepository.findByChatUserId(chatId)
                .orElseGet(() -> userRepository.save(new User(chatId, name, State.START)));
    }

    private User getUser(CallbackQuery callbackQuery) {
        final int chatId = callbackQuery.getFrom().getId();
        final String name = callbackQuery.getFrom().getFirstName();

        return userRepository.findByChatUserId(chatId)
                .orElseGet(() -> userRepository.save(new User(chatId, name, State.START)));
    }

    private Handler getHandlerByState(State state) {
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private Handler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery().stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }
}
