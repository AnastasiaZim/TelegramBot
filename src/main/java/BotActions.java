import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class BotActions {
    static SendMessage getSendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        return addButtons(sendMessage);
    }

    static SendMessage getSendMessage(long chatId, String text, int replyId) {
        SendMessage sendMessage = getSendMessage(chatId, text);
        sendMessage.setReplyToMessageId(replyId);
        return sendMessage;
    }

    static SendMessage getInlineKeyboard(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Автор");
        inlineKeyboardButton1.setCallbackData("Анастасия Зимницкая, 2-пми-4");
        inlineKeyboardButton2.setText("Какой год?");
        inlineKeyboardButton2.setCallbackData("На дворе " + Calendar
                .getInstance()
                .get(Calendar.YEAR));
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage()
                .setChatId(chatId)
                .setText("Кнопочки")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    private static SendMessage addButtons(SendMessage sendMessage) {
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/hello"));
        keyboardFirstRow.add(new KeyboardButton("/time"));
        keyboardFirstRow.add(new KeyboardButton("/info"));
        keyboardRowList.add(keyboardFirstRow);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
