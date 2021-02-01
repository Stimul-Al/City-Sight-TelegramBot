package by.home.telegrambot.util;

import by.home.telegrambot.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class TelegramUtil {

    public static SendMessage createMessageTemplate(User user) {
        return createMessageTemplate(String.valueOf(user.getChatId()));
    }

    public static SendMessage createMessageTemplate(User user,
                                                    String text,
                                                    InlineKeyboardMarkup markup) {
        return SendMessage.builder()
                .chatId(String.valueOf(user.getChatId()))
                .text(text)
                .replyMarkup(markup)
                .build();
    }

    public static SendMessage createMessageTemplate(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);

        return sendMessage;
    }

    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(command)
                .build();
    }

    public static InlineKeyboardMarkup createInlineKeyboardMarkup(List<InlineKeyboardButton> inlineKeyboardButtons) {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(inlineKeyboardButtons))
                .build();
    }
}
