package ru.yandex.sbd.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yandex.sbd.messenger.entities.Chat;
import ru.yandex.sbd.messenger.entities.Message;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {
    @Query("SELECT 1 FROM Message")
    void isDbConnected();

    List<Message> findAllByChat(Chat chat);

    @Query(value = "select * from messages where text like CONCAT('%', :text ,'%')\n" +
            "and chat_id in (select chat_id from users_chats_link where user_id =:userId)",
            nativeQuery = true)
    List<Message> findMessageByTextContains(@Param("userId") String userId, @Param("text") String text);
}
