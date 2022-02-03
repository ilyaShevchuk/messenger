package ru.yandex.sbd.messenger.service;

import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.MessagesOut;
import ru.yandex.sbd.messenger.models.dto.ChatDto;
import ru.yandex.sbd.messenger.models.dto.MessageDto;
import ru.yandex.sbd.messenger.models.requestbody.ChatRequestBody;
import ru.yandex.sbd.messenger.models.requestbody.MessageRequestBody;

public interface IChatsService {
    ChatDto createChat(ChatRequestBody chatBody);

    MessagesOut getMessagesFromChat(String chatId, String userId, int limit, Cursor cursor);

    MessageDto sendMessageToChat(String chatId, String userId, MessageRequestBody messageBody);

    boolean isDbConnected();
}
