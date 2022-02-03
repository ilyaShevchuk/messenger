package ru.yandex.sbd.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.sbd.messenger.entities.Task;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {
    Task findTaskById(Long id);

    Task findBySessionId(String sessionId);
}
