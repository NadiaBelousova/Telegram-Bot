package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private NotificationTaskService notificationTaskService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationTaskService notificationTaskService) {
        this.telegramBot = telegramBot;
        this.notificationTaskService = notificationTaskService;
    }

    public TelegramBotUpdatesListener() {
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String greetingMessage = "Hello! Do you want to create a new task? Please, enter your task in right format: dd.MM.yyyy HH:mm text message";
            Long chatId = update.message().chat().id();
            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
            Matcher matcher = pattern.matcher(update.message().text());
            if (update.message().text().equals("/start")) {
                SendMessage sendMessage = new SendMessage(chatId, greetingMessage);
                telegramBot.execute(sendMessage);
            } else if (matcher.matches()) {
                String date = matcher.group(1);
                String textMessage = matcher.group(3);
                LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                NotificationTask notificationTask = new NotificationTask(chatId, textMessage, localDateTime);
                notificationTaskService.createTask(notificationTask);
            } else {
                telegramBot.execute(new SendMessage(chatId, "error, please check your task is entered correctly"));
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
