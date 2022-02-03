package ru.yandex.sbd.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.sbd.messenger.entities.User;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    @Query("select 1 from User")
    void isDbConnected();

    User findUserByLogin(String login);

    User findUserById(String id);
}
