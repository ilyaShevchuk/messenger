package ru.yandex.sbd.messenger.entities;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.sbd.messenger.config.TaskStatus;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @JoinColumn(name = "session_id")
    private String sessionId;

    @Getter
    @Setter
    @JoinColumn(name = "status")
    private TaskStatus status;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<FoundMessage> messages = new LinkedList<>();


    public Task(String sessionId) {
        this.sessionId = sessionId;
        this.status = TaskStatus.PROCESSED;
    }

    public Task() {
    }
}
