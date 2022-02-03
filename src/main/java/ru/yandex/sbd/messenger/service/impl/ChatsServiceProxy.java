package ru.yandex.sbd.messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.sbd.messenger.bot.HelpBot;
import ru.yandex.sbd.messenger.config.csvConfig.ConfigReader;
import ru.yandex.sbd.messenger.entities.Message;
import ru.yandex.sbd.messenger.exceptions.BadParamsException;
import ru.yandex.sbd.messenger.exceptions.DatabaseConnectionException;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.MessagesOut;
import ru.yandex.sbd.messenger.models.dto.ChatDto;
import ru.yandex.sbd.messenger.models.dto.MessageDto;
import ru.yandex.sbd.messenger.models.requestbody.ChatRequestBody;
import ru.yandex.sbd.messenger.models.requestbody.MessageRequestBody;
import ru.yandex.sbd.messenger.service.IChatsService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatsServiceProxy implements IChatsService {
    private final IChatsService service;
    private final ConfigReader configReader;

    @Autowired
    public ChatsServiceProxy(IChatsService chatsService) {
        this.service = chatsService;
        this.configReader = new ConfigReader();
        configReader.readConfig();
    }

    @Override
    public ChatDto createChat(ChatRequestBody chatBody) {
        if (!isDbConnected()) {
            throw new DatabaseConnectionException("Database is not connected");
        }
        return service.createChat(chatBody);
    }

    @Override
    public MessagesOut getMessagesFromChat(String chatId, String userId, int limit, Cursor cursor) {
        if (!isDbConnected()) {
            HelpBot bot = new HelpBot();
            List<MessageDto> botMessages = new ArrayList<>();
            if (userId == null) {
                throw new BadParamsException("getMessages from proxy without userId");
            }
            if (!configReader.getUsersInConfig().containsKey(userId)) {
                botMessages.add(MessageDto.toDto(new Message(bot.giveDefaultGreeting())));
            } else {
                botMessages.add(MessageDto.toDto(new Message(bot.givePersonalizeGreeting(
                        configReader.getUsersInConfig().get(userId)))));
            }
            return new MessagesOut(botMessages, 1);
        }
        return service.getMessagesFromChat(chatId, userId, limit, cursor);
    }

    @Override
    public MessageDto sendMessageToChat(String chatId, String userId, MessageRequestBody messageBody) {
        if (!isDbConnected()) {
            throw new DatabaseConnectionException("Database is not connected");
        }
        return service.sendMessageToChat(chatId, userId, messageBody);
    }

    @Override
    public boolean isDbConnected() {
        return service.isDbConnected();
    }
}
