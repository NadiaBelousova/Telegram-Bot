package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long chatId;

    private String textMessage;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public NotificationTask() {
    }

    public NotificationTask(long chatId, String textMessage, LocalDateTime dateTime) {
        this.chatId = chatId;
        this.textMessage = textMessage;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public LocalDateTime getLocalDateTime() {
        return dateTime;
    }

    public void setLocalDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationTask)) return false;
        NotificationTask that = (NotificationTask) o;
        return id == that.id && chatId == that.chatId && Objects.equals(textMessage, that.textMessage) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, textMessage, dateTime);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", textMessage='" + textMessage + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
