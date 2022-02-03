package ru.yandex.sbd.messenger.service;

import org.junit.Test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.sbd.messenger.entities.Chat;
import ru.yandex.sbd.messenger.entities.User;
import ru.yandex.sbd.messenger.exceptions.ChatIdNotFoundException;
import ru.yandex.sbd.messenger.models.dto.ChatDto;
import ru.yandex.sbd.messenger.models.dto.UserDto;
import ru.yandex.sbd.messenger.models.requestbody.ChatRequestBody;
import ru.yandex.sbd.messenger.models.requestbody.UserRequestBody;
import ru.yandex.sbd.messenger.repositories.ChatsRepository;
import ru.yandex.sbd.messenger.repositories.UsersRepository;
import ru.yandex.sbd.messenger.service.impl.ChatsService;
import ru.yandex.sbd.messenger.service.impl.UserService;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest extends AbstractServiceTest{

    @Mock
    ChatsRepository chatsRepository;

    @InjectMocks
    ChatsService chatsService;


    @Test
    public void Should_CreateChat_When_ExistName() {
        when(chatsRepository.save(any(Chat.class)))
                .thenReturn(new Chat(CHAT_NAME));


        ChatDto chat = chatsService.createChat(new ChatRequestBody(CHAT_NAME));
        CHAT_ID = chat.getId();
        Assertions.assertThat(CHAT_NAME)
                .isEqualTo(chatsRepository.findById(chat.getId()).get().getName());
    }

}
