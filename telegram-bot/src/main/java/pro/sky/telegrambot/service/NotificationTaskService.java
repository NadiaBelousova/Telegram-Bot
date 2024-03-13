package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service

public class NotificationTaskService {

    @Autowired
    private final TelegramBot telegramBot;

    @Autowired
    private final NotificationRepository notificationRepository;

    public NotificationTaskService(TelegramBot telegramBot, NotificationRepository notificationRepository) {
        this.telegramBot = telegramBot;
        this.notificationRepository = notificationRepository;
    }

    Logger logger = LoggerFactory.getLogger(NotificationTaskService.class);

    public NotificationTask createTask(NotificationTask notificationTask) {
        logger.info("вызван метод создания задачи");
        return notificationRepository.save(notificationTask);

    }

    public List<NotificationTask> findAllByLocalDateTime(LocalDateTime localDateTime) {
        return notificationRepository.findAllByDateTime(localDateTime);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void remind() {
        findAllByLocalDateTime(LocalDateTime.now()
                        .truncatedTo(ChronoUnit.MINUTES))
                .forEach(reminder -> {
                    telegramBot.execute(new SendMessage(reminder.getChatId(),
                            reminder.getTextMessage()));
                });
    }

}

