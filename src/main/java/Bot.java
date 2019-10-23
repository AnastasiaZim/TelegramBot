import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


public class Bot extends TelegramLongPollingCommandBot {
    private final static String BOT_NAME = "MyTestBot";

    private Bot() {
        super(getBotOptions(), BOT_NAME);
        register(new SqrtCommand());
        register(new TimeCommand());
    }

    private static DefaultBotOptions getBotOptions() {
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost("47.91.88.100");
        botOptions.setProxyPort(1080);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return botOptions;
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                switch (update.getMessage().getText()) {
                    case "/start":
                        execAction(BotActions.getSendMessage(
                                update.getMessage().getChatId(),
                                "Ну давай начнем"));
                        break;
                    case "/hello":
                        execAction(BotActions.getSendMessage(
                                update.getMessage().getChatId(),
                                "Ну привет",
                                update.getMessage().getMessageId()));
                        break;
                    case "/info":
                        execAction(BotActions.getInlineKeyboard(update.getMessage().getChatId()));
                        break;
                    default:
                        if (update
                                .getMessage()
                                .getText()
                                .startsWith("/")) {
                            execAction(BotActions.getSendMessage(
                                    update
                                            .getMessage()
                                            .getChatId(),
                                    "Это команда такая? Такой у меня нет, но есть другие, например /sqrt:",
                                    update
                                            .getMessage()
                                            .getMessageId())
                            );
                        }
                }
            }
        } else if (update.hasCallbackQuery()) {
            execAction(BotActions.getSendMessage(
                    update
                            .getCallbackQuery()
                            .getMessage().
                            getChatId(),
                    update
                            .getCallbackQuery()
                            .getData())
            );
        }
    }

    private void execAction(BotApiMethod action) {
        try {
            execute(action);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotToken() {
        return "842016954:AAHwHJRQo6zjixPnbZBBp0J9UHU4Ly0M6eY";
    }
}
