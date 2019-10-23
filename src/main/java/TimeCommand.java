import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Calendar;

public class TimeCommand extends BotCommand {
    TimeCommand() {
        super("/time", "Могу показать время");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        int hour = Calendar
                .getInstance()
                .get(Calendar.HOUR);
        int min = Calendar
                .getInstance()
                .get(Calendar.MINUTE);
        try {
            absSender.execute(BotActions.getSendMessage(chat.getId(), hour + ":" + min));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
