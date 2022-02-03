package ru.yandex.sbd.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.sbd.messenger.config.TaskStatus;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.dto.TaskDto;
import ru.yandex.sbd.messenger.models.requestbody.CreateSearchTaskBody;
import ru.yandex.sbd.messenger.models.requestbody.HistoryMessagesOut;
import ru.yandex.sbd.messenger.responses.IResponse;
import ru.yandex.sbd.messenger.responses.impl.GetHistoryResponse;
import ru.yandex.sbd.messenger.responses.impl.GetTaskResponse;
import ru.yandex.sbd.messenger.responses.impl.HistoryGetMessagesResponse;
import ru.yandex.sbd.messenger.service.impl.TaskServiceImpl;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@Validated
public class SearchMessageController {

    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping("/v1/chats/search")
    public ResponseEntity<IResponse> createSearchTaskMapping(
            @RequestBody CreateSearchTaskBody searchTaskBody,
            @RequestHeader("Authorization") String sessionId) {
        TaskDto task = taskService.createTask(sessionId, searchTaskBody.getMessage());

        return new ResponseEntity<>(new GetHistoryResponse(task.getTaskId().toString()), HttpStatus.CREATED);
    }

    @GetMapping("/v1/chats/search/{task_id}")
    public ResponseEntity<IResponse> getTaskStatus(
            @PathVariable("task_id") String taskId,
            @RequestHeader("Authorization") String sessionId) {
        TaskStatus status = taskService.getTaskStatus(Long.parseLong(taskId), sessionId);
        return new ResponseEntity<>(new GetTaskResponse(status.toString()), HttpStatus.CREATED);
    }

    @GetMapping("/v1/chats/search/{task_id}/messages")
    public ResponseEntity<IResponse> getMessagesFromTask(
            @PathVariable("task_id") String taskId,
            @RequestParam("limit") @Min(1) @Max(1000) int limit,
            @Nullable @RequestParam("from") Cursor cursor,
            @RequestHeader("Authorization") String sessionId) {
        HistoryMessagesOut messages = taskService.getMessages(Long.parseLong(taskId), sessionId, limit, cursor);
        return new ResponseEntity<>(new HistoryGetMessagesResponse(messages.getMessagesDtoList(),
                new Cursor(String.valueOf(messages.getCursor()))), HttpStatus.CREATED);
    }

}
