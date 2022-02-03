package ru.yandex.sbd.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.sbd.messenger.entities.Chat;

@Repository
public interface ChatsRepository extends JpaRepository<Chat, String> {
    @Query("select 1 from Chat")
    void isDbConnected();
}
