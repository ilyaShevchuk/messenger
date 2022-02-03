package ru.yandex.sbd.messenger.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "found_messages")
public class FoundMessage {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Setter
    @Getter
    @Column(name = "text")
    private String text;

    @Setter
    @Getter
    @Column(name = "chat_id")
    private String chatId;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;


    public FoundMessage() {
    }


    public FoundMessage(String text, String chatId, Task task) {
        this.text = text;
        this.chatId = chatId;
        this.task = task;
    }

    public FoundMessage(Message message, Task task) {
        this.text = message.getText();
        this.chatId = message.getChat().getId();
        this.task = task;
    }

}
