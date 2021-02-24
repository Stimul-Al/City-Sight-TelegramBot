package by.home.telegrambot.util;

import by.home.telegrambot.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.List;

public class TelegramUtil {

    public static SendMessage createMessageTemplate(User user) {
        return createMessageTemplate(String.valueOf(user.getChatUserId()));
    }

    public static SendMessage createMessageTemplate(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);

        return sendMessage;
    }

    public static SendMessage createMessageTemplate(User user,
                                                    String text,
                                                    InlineKeyboardMarkup markup) {
        return SendMessage.builder()
                .chatId(String.valueOf(user.getChatUserId()))
                .text(text)
                .parseMode("Markdown")
                .replyMarkup(markup)
                .build();
        //[]⁠
    }

    public static SendPhoto createPhotoTemplate(User user, String text, InlineKeyboardMarkup markup) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(user.getChatUserId()));
        sendPhoto.setPhoto(new InputFile("https://static.probusiness.io/720x480c/n/03/d/38097027_439276526579800_2735888197547458560_n.jpg"));
        //new File("src/main/resources/static/cat_with_mustache.jpg"))
        sendPhoto.setCaption(text);
        sendPhoto.setReplyMarkup(markup);

        return sendPhoto;
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
