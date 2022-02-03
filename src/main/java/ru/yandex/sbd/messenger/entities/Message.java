package ru.yandex.sbd.messenger.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Getter
    @Setter
    @Column(name = "id")
    private String id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_from")
    private User userFrom;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT", name = "text")
    private String text;

    public Message(String text) {
        this.text = text;
    }

    public Message(String text, Chat ChatId, User userFrom) {
        this.text = text;
        this.chat = ChatId;
        this.userFrom = userFrom;
    }
    public Message() {
    }
}
