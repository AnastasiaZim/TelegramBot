import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Bot extends TelegramLongPollingBot {
    private Bot() {
        super(getBotOptions());
    }

    private static DefaultBotOptions getBotOptions(){
        DefaultBotOptions botOptions= ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost("129.146.181.251");
        botOptions.setProxyPort(3128);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
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

    private void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message,"Помогаю");
                    sendMsg(message,"(нет)");
                    break;
                case "/lapka":
                    sendMsg(message,"У меня тоже");
                    break;
                default:
                    sendMsg(message,"Ничего не поняла, но очень рада за вас");
            }
        }
    }

    public String getBotUsername() {
        return "MyTestBot";
    }

    public String getBotToken() {
        return "842016954:AAHwHJRQo6zjixPnbZBBp0J9UHU4Ly0M6eY";
    }
}
