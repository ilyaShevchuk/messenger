package ru.yandex.sbd.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.MessagesOut;
import ru.yandex.sbd.messenger.models.dto.ChatDto;
import ru.yandex.sbd.messenger.models.dto.MessageDto;
import ru.yandex.sbd.messenger.models.dto.UserDto;
import ru.yandex.sbd.messenger.models.requestbody.AuthUserBody;
import ru.yandex.sbd.messenger.models.requestbody.ChatRequestBody;
import ru.yandex.sbd.messenger.models.requestbody.MessageRequestBody;
import ru.yandex.sbd.messenger.models.requestbody.UserRequestBody;
import ru.yandex.sbd.messenger.responses.IResponse;
import ru.yandex.sbd.messenger.responses.impl.ChatCreateResponse;
import ru.yandex.sbd.messenger.responses.impl.ChatGetMessagesResponse;
import ru.yandex.sbd.messenger.responses.impl.ChatJoinResponse;
import ru.yandex.sbd.messenger.responses.impl.ChatSendMessageResponse;
import ru.yandex.sbd.messenger.service.IChatsService;
import ru.yandex.sbd.messenger.service.impl.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/v1/chats")
@Validated
public class ChatsController {
    private final IChatsService chatsService;
    private final UserService userService;

    @Autowired
    public ChatsController(IChatsService chatsService, UserService userService) {
        this.chatsService = chatsService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<IResponse> createChat(@Valid @RequestBody ChatRequestBody chatBody) {
        ChatDto chat = chatsService.createChat(chatBody);
        return new ResponseEntity<>(new ChatCreateResponse(chat.getId()), HttpStatus.CREATED);
    }

    @PostMapping("/{chat_id}/users")
    public ResponseEntity<IResponse> addUserToChat(@PathVariable("chat_id") String chatId,
                                                   @Valid @RequestBody UserRequestBody userBody) {
        UserDto user = userService.addUserToChat(chatId, userBody);
        return new ResponseEntity<>(new ChatJoinResponse(user.getId()), HttpStatus.CREATED);
    }

    @PostMapping("/{chat_id}/users2")
    public ResponseEntity<IResponse> addAuthUserToChat(@PathVariable("chat_id") String chatId,
                                                       @Valid @RequestBody AuthUserBody userBody) {
        UserDto user = userService.addAuthUserToChat(chatId, userBody);
        return new ResponseEntity<>(new ChatJoinResponse(user.getId()), HttpStatus.CREATED);
    }


    @GetMapping("/{chat_id}/messages")
    public ResponseEntity<IResponse> getMessagesFromChat(@PathVariable("chat_id") String chatId,
                                                         @Nullable @RequestHeader("user_id") String userId,
                                                         @RequestParam("limit") @Min(1) @Max(1000) int limit,
                                                         @Nullable @RequestParam("from") Cursor cursor) {
        MessagesOut messages = chatsService.getMessagesFromChat(chatId, userId, limit, cursor);
        return new ResponseEntity<>(new ChatGetMessagesResponse(messages.getMessagesDtoList(), messages.getCursor()),
                HttpStatus.OK);
    }

    @PostMapping("/{chat_id}/messages")
    public ResponseEntity<IResponse> sendMessageToChat(@PathVariable("chat_id") String chatId,
                                                       @RequestParam("user_id") String userId,
                                                       @Valid @RequestBody MessageRequestBody messageBody) {
        MessageDto message = chatsService.sendMessageToChat(chatId, userId, messageBody);
        return new ResponseEntity<>(new ChatSendMessageResponse(message), HttpStatus.CREATED);
    }

    @PostMapping("/db_ping")
    public ResponseEntity<IResponse> dbPing() {
        return chatsService.isDbConnected() ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
