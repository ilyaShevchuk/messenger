package ru.yandex.sbd.messenger.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
public class User {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFrom")
    private final List<Message> messages = new LinkedList<>();
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @JsonProperty("user_id")
    private String id;
    @Column(name = "time_zone_id",
            columnDefinition = "varchar(10) default '+3'")
    private String timeZoneId;
    @Column
    private String login;
    @Column
    private String password;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Chat> chats = new ArrayList<>();

    public User(String name, String timeZoneId) {
        this.name = name;
        this.timeZoneId = timeZoneId;
    }

    public User() {
    }

    public User(String name, String login, String password, String timeZone) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.timeZoneId = timeZone;
    }
}
