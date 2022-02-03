package ru.yandex.sbd.messenger.service;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.sbd.messenger.entities.Chat;
import ru.yandex.sbd.messenger.entities.User;
import ru.yandex.sbd.messenger.exceptions.ChatIdNotFoundException;
import ru.yandex.sbd.messenger.models.dto.UserDto;
import ru.yandex.sbd.messenger.models.requestbody.AuthUserBody;
import ru.yandex.sbd.messenger.models.requestbody.UserRequestBody;
import ru.yandex.sbd.messenger.repositories.ChatsRepository;
import ru.yandex.sbd.messenger.repositories.UsersRepository;
import ru.yandex.sbd.messenger.service.impl.UserService;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends AbstractServiceTest{

    @Mock
    ChatsRepository chatsRepository;

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void Should_JoinUserToChat_When_ExistChat() {
        when(chatsRepository.findById(CHAT_ID))
                .thenReturn(Optional.of(new Chat()));
        when(usersRepository.save(any(User.class)))
                .thenReturn(new User(USER_NAME, TIME_ZONE));

        UserDto chatUser = userService.addUserToChat(CHAT_ID, new UserRequestBody(USER_NAME, TIME_ZONE));
        User user = usersRepository.findUserById(chatUser.getId());
        Assertions.assertThat(user.getName())
                .isEqualTo(USER_NAME);
    }

    @Test
    public void Should_JoinAuthUserToChat_When_ExistChat() {
        when(chatsRepository.findById(CHAT_ID))
                .thenReturn(Optional.of(new Chat()));

        UserDto userDto = userService.registerUser(new User(AUTH_USER_NAME, AUTH_LOGIN, PLANK_PASSWORD, TIME_ZONE));
        AUTH_USER_ID = userDto.getId();
        UserDto chatUser = userService.addAuthUserToChat(CHAT_ID, new AuthUserBody(AUTH_USER_ID));
        User user = usersRepository.findUserById(chatUser.getId());
        Assertions.assertThat(user.getName())
                .isEqualTo(AUTH_USER_NAME);
    }

    @Test
    public void Should_ThrowException_When_NotExistChat() {
        when(chatsRepository.findById(CHAT_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.addUserToChat(CHAT_ID, new UserRequestBody(USER_NAME, TIME_ZONE)))
                .isInstanceOf(ChatIdNotFoundException.class);
    }

    @Test
    public void Should_ThrowException_When_Auth_And_NotExistChat() {
        when(chatsRepository.findById(CHAT_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.addAuthUserToChat(CHAT_ID, new AuthUserBody(USER_ID)))
                .isInstanceOf(ChatIdNotFoundException.class);
    }
    @Test
    public void Should_FindUserByLogin_When_ExistUser(){
        when(usersRepository.findUserByLogin(LOGIN))
                .thenReturn(new User(USER_NAME, TIME_ZONE));

        Optional<User> user = userService.findUserByLogin(LOGIN);
        Assertions.assertThat(user.get().getName())
                .isEqualTo(USER_NAME);
    }

    @Test
    public void Should_FindUserByLogin_When_NoUserWithLogin(){
        when(usersRepository.findUserByLogin(LOGIN))
                .thenReturn(null);

        Optional<User> user = userService.findUserByLogin(LOGIN);
        Assertions.assertThat(user.get())
                .isEqualTo(Optional.empty());
    }

}
