import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class SqrtCommand extends BotCommand {
    SqrtCommand() {
        super("/sqrt", "Вычислю квадратный корень за тебя");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String text;
        String name;
        if (user.getUserName() == null) {
            name = user.getFirstName();
        } else {
            name = user.getUserName();
        }
        if (strings.length > 0) {
            try {
                double number = Double.parseDouble(strings[0]);
                if (number < 0) {
                    text = "Как я из отрицательного числа " + strings[0] + " должна брать корень?";
                } else {
                    text = "Вот твой корень: " + Math.sqrt(number) + " из числа " + number + ", обращайся, " + name;
                }
            } catch (NumberFormatException e) {
                text = "А давай ты мне нормальную цифру отправишь, а не '" + strings[0] + "'?";
            }
        } else {
            text = "А можно мне цифру, пожалуйста?";
        }
        try {
            absSender.execute(BotActions.getSendMessage(chat.getId(), text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
