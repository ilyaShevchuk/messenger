package ru.yandex.sbd.messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.yandex.sbd.messenger.config.TaskStatus;
import ru.yandex.sbd.messenger.config.jwt.JwtProvider;
import ru.yandex.sbd.messenger.entities.FoundMessage;
import ru.yandex.sbd.messenger.entities.Message;
import ru.yandex.sbd.messenger.entities.Task;
import ru.yandex.sbd.messenger.entities.User;
import ru.yandex.sbd.messenger.exceptions.TaskNotCompletedException;
import ru.yandex.sbd.messenger.exceptions.TaskNotFoundException;
import ru.yandex.sbd.messenger.exceptions.UserIdNotFoundException;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.dto.HistoryMessageDto;
import ru.yandex.sbd.messenger.models.dto.TaskDto;
import ru.yandex.sbd.messenger.models.requestbody.HistoryMessagesOut;
import ru.yandex.sbd.messenger.repositories.FoundMessagesRepository;
import ru.yandex.sbd.messenger.repositories.MessagesRepository;
import ru.yandex.sbd.messenger.repositories.TasksRepository;
import ru.yandex.sbd.messenger.repositories.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private FoundMessagesRepository foundMessagesRepository;
    @Autowired
    private MessagesRepository messagesRepository;

    public TaskDto createTask(String sessionId, String pattern) {
        validateToken(sessionId);
        String login = jwtProvider.getLoginFromToken(sessionId);
        User user = usersRepository.findUserByLogin(login);
        if (user == null) {
            throw new UserIdNotFoundException("User not found");
        }
        Task task = new Task(sessionId);
        startTask(task, user, pattern);
        tasksRepository.save(task);
        return new TaskDto(task.getId());
    }

    public boolean updateTaskStatus(Long taskId, TaskStatus status) {
        try {
            Task task = getTask(taskId);
            task.setStatus(status);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public TaskStatus getTaskStatus(Long taskId, String sessionId) {
        validateToken(sessionId);
        return getTask(taskId).getStatus();
    }

    private Task getTask(Long taskId) {
        if (tasksRepository.findTaskById(taskId) == null) {
            throw new TaskNotFoundException("Tasks with " + taskId + " not found");
        }
        return tasksRepository.findTaskById(taskId);
    }

    public HistoryMessagesOut getMessages(Long taskId, String sessionId, int limit, Cursor cursor) throws TaskNotCompletedException, TaskNotFoundException {
        validateToken(sessionId);
        if (cursor == null) {
            cursor = new Cursor("0");
        }
        int from = Integer.parseInt(cursor.getIterator());
        Task task = tasksRepository.findBySessionId(sessionId);
        if (task == null) {
            throw new TaskNotFoundException("No access");
        }

        if (task.getStatus() != TaskStatus.COMPLETED) {
            throw new TaskNotCompletedException("Task not completed with id " + taskId);
        }
        List<FoundMessage> messages = foundMessagesRepository.findByTask_Id(taskId).subList(from, limit);
        List<HistoryMessageDto> historyMessages = messages.stream().map(HistoryMessageDto::toDto)
                .collect(Collectors.toList());
        return new HistoryMessagesOut(historyMessages, limit + from + 1);
    }

    @Async
    protected void asyncFindMessagesByQuery(String pattern, Task task, String userId) {
        List<Message> messages = messagesRepository.findMessageByTextContains(userId, pattern);
        for (Message message : messages) {
            FoundMessage foundMessage = new FoundMessage(message, task);
            foundMessagesRepository.save(foundMessage);
        }
//        var foundMessages = messages.stream().map(FoundMessage::new).collect(Collectors.toList());
//        messagesRepository.saveAll()
        task.setStatus(TaskStatus.COMPLETED);
    }

    private void validateToken(String sessionId) throws UserIdNotFoundException {
        if (!jwtProvider.validateToken(sessionId)) {
            throw new UserIdNotFoundException("jwt token is wrong");
        }
    }

    private void startTask(Task task, User user, String pattern) {
        asyncFindMessagesByQuery(pattern, task, user.getId());
    }

}
