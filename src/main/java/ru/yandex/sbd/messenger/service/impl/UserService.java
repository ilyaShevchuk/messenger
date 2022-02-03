package ru.yandex.sbd.messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yandex.sbd.messenger.entities.User;
import ru.yandex.sbd.messenger.exceptions.ChatIdNotFoundException;
import ru.yandex.sbd.messenger.models.dto.UserDto;
import ru.yandex.sbd.messenger.models.requestbody.AuthUserBody;
import ru.yandex.sbd.messenger.models.requestbody.UserRequestBody;
import ru.yandex.sbd.messenger.repositories.ChatsRepository;
import ru.yandex.sbd.messenger.repositories.UsersRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private ChatsRepository chatsRepo;
    @Autowired
    private UsersRepository usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto addUserToChat(String chatId, UserRequestBody userBody) {
        if (!chatsRepo.existsById(chatId)) {
            throw new ChatIdNotFoundException(String.format("Chat with id %s does not exist", chatId));
        }
        User user = new User(userBody.getName(), userBody.getTimeZone());
        chatsRepo.getById(chatId).getUsers().add(user);
        usersRepo.save(user);
        return new UserDto(user.getId());
    }

    public UserDto addAuthUserToChat(String chatId, AuthUserBody userBody) {
        if (!chatsRepo.existsById(chatId)) {
            throw new ChatIdNotFoundException(String.format("Chat with id %s does not exist", chatId));
        }
        User user = usersRepo.findUserById(userBody.getUserId());
        chatsRepo.getById(chatId).getUsers().add(user);
        usersRepo.save(user);
        return new UserDto(user.getId());
    }


    public UserDto registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepo.save(user);
        return new UserDto(user.getId());
    }

    public Optional<User> findUserByLogin(String login) {
        User user = usersRepo.findUserByLogin(login);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    public Optional<User> findByLoginAndPassword(String name, String password) {
        Optional<User> user = findUserByLogin(name);
        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user;
            }
        }
        return Optional.empty();
    }
}
