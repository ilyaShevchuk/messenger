package ru.yandex.sbd.messenger.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.sbd.messenger.entities.Chat;
import ru.yandex.sbd.messenger.entities.Message;
import ru.yandex.sbd.messenger.entities.User;
import ru.yandex.sbd.messenger.exceptions.ChatIdNotFoundException;
import ru.yandex.sbd.messenger.exceptions.UserIdNotFoundException;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.MessagesOut;
import ru.yandex.sbd.messenger.models.dto.MessageDto;
import ru.yandex.sbd.messenger.models.requestbody.MessageRequestBody;
import ru.yandex.sbd.messenger.repositories.ChatsRepository;
import ru.yandex.sbd.messenger.repositories.MessagesRepository;
import ru.yandex.sbd.messenger.repositories.UsersRepository;
import ru.yandex.sbd.messenger.service.impl.ChatsService;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceAfterAddUserTest extends AbstractServiceTest{

    private final Chat CHAT = new Chat(CHAT_NAME);
    private final User USER = new User(USER_NAME, LOGIN, PLANK_PASSWORD, TIME_ZONE);
    private final Cursor CURSOR = new Cursor("0");

    @Mock
    ChatsRepository chatsRepository;

    @Mock
    MessagesRepository messagesRepository;

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    ChatsService chatsService;

    @BeforeEach
    public void setUp () {
        when(chatsRepository.findById(CHAT_ID))
                .thenReturn(Optional.of(CHAT));
        CHAT.setUsers(Collections.singletonList(USER));
        CHAT.setMessageLog(Arrays.asList(new Message(MESSAGE_TEXT, CHAT, USER),
                new Message(MESSAGE_TEXT + "2", CHAT, USER)));
    }

    @Test
    public void Should_SendMessage_When_UserInChat() {
        Message expectedMessage =
                new Message(MESSAGE_TEXT, CHAT, USER);

        when(usersRepository.findById(USER_ID))
                .thenReturn(Optional.of(USER));
        when(messagesRepository.save(any(Message.class)))
                .thenReturn(expectedMessage);

        MessageDto actualMessageDto = chatsService.sendMessageToChat(CHAT_ID, USER_ID, new MessageRequestBody(MESSAGE_TEXT));
        String actualMessageText = actualMessageDto.getText();
        Assertions.assertThat(actualMessageText)
                .isEqualTo(MESSAGE_TEXT);
    }

    @Test
    public void Should_SendMessage_When_UserNotInChat() {
       when(usersRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> chatsService.sendMessageToChat(CHAT_ID, USER_ID,
                new MessageRequestBody(MESSAGE_TEXT)))
                .isInstanceOf(UserIdNotFoundException.class);
    }

    @Test
    public void Should_GetTwoMessages_When_MessagesExistsAndLimit() {
        List<Message> expectedMessages = Arrays.asList(
                new Message(MESSAGE_TEXT, CHAT, USER),
                new Message(MESSAGE_TEXT + "2", CHAT, USER));

        when(chatsRepository.findById(CHAT_ID))
                .thenReturn(Optional.of(CHAT));

        MessagesOut actualMessages = chatsService.getMessagesFromChat(CHAT_ID, USER_ID, 2, CURSOR);
        Assertions.assertThat(actualMessages.getMessagesDtoList().stream()
                .map(MessageDto::getText)
                .collect(Collectors.toList()))
                .isEqualTo(expectedMessages.stream()
                .map(Message::getText)
                .collect(Collectors.toList()));
    }

    @Test
    public void Should_NotGetMessages_When_ChatNotExists() {
        when(chatsRepository.findById(CHAT_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> chatsService.getMessagesFromChat(CHAT_ID, USER_ID, 2, CURSOR))
                .isInstanceOf(ChatIdNotFoundException.class);
    }
}
