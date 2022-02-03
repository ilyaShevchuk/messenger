package ru.yandex.sbd.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.sbd.messenger.entities.FoundMessage;

import java.util.List;

@Repository
public interface FoundMessagesRepository extends JpaRepository<FoundMessage, Long> {
    List<FoundMessage> findByTask_Id(Long task_id);
}
