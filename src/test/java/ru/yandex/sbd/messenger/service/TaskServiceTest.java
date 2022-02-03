package ru.yandex.sbd.messenger.service;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.sbd.messenger.config.TaskStatus;
import ru.yandex.sbd.messenger.entities.*;
import ru.yandex.sbd.messenger.exceptions.UserIdNotFoundException;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.dto.HistoryMessageDto;
import ru.yandex.sbd.messenger.models.dto.TaskDto;
import ru.yandex.sbd.messenger.models.requestbody.HistoryMessagesOut;
import ru.yandex.sbd.messenger.repositories.*;
import ru.yandex.sbd.messenger.service.impl.TaskServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest extends AbstractServiceTest{
    private final Chat CHAT = new Chat(CHAT_NAME);
    private final User USER = new User(USER_NAME, LOGIN, PLANK_PASSWORD, TIME_ZONE);
    private final Cursor CURSOR = new Cursor("0");

    @Mock
    TasksRepository searchTaskRepository;

    @Mock
    FoundMessagesRepository foundMessagesRepository;

    @Mock
    ChatsRepository chatsRepository;

    @Mock
    UsersRepository usersRepository;


    @InjectMocks
    TaskServiceImpl taskService;

    @BeforeEach
    public void setUp () {
        when(chatsRepository.findById(CHAT_ID))
                .thenReturn(Optional.of(CHAT));
        CHAT.setUsers(Collections.singletonList(USER));
    }

    @Test
    public void Should_GetTask_When_TaskExistsAndLoginIsOwnerTask() {
        Task expected = new Task(SESSION_ID);

        when(searchTaskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(expected));

        TaskDto actual = taskService.createTask(SESSION_ID, SEARCH_TEXT);

        Assertions.assertThat(actual)
                .extracting(TaskDto::getTaskId)
                .isEqualTo(TASK_ID);
    }

    @Test
    public void Should_GetTask_When_WrongLogin() {
        Task expected = new Task(SESSION_ID);

        when(searchTaskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(expected));

        Assertions.assertThatThrownBy(() -> taskService.createTask(SESSION_ID, SEARCH_TEXT))
                .isInstanceOf(UserIdNotFoundException.class);
    }

    @Test
    public void Should_GetSearchResults_When_SearchResultsExist() {
        int limit = 2;
        int cursor = 0;
        Task task = new Task(SESSION_ID);

        List<FoundMessage> expected = Arrays.asList(
                new FoundMessage(new Message("Hello", CHAT, USER), task),
                new FoundMessage(new Message("Hello2", CHAT, USER), task)
        );

        when(searchTaskRepository.findById(TASK_ID))
                .thenReturn(Optional.of(task));
        when(foundMessagesRepository.findByTask_Id(TASK_ID))
                .thenReturn(expected);

        HistoryMessagesOut actualResults = taskService.getMessages(TASK_ID, SESSION_ID, limit, CURSOR);

        Assertions.assertThat(actualResults.getMessagesDtoList().stream()
                .map(HistoryMessageDto::getText)
                .collect(Collectors.toList()))
                .isEqualTo(expected.stream()
                        .map(FoundMessage::getText)
                        .collect(Collectors.toList()));
    }



}
