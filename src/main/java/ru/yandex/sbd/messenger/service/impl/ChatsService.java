package ru.yandex.sbd.messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.yandex.sbd.messenger.entities.Chat;
import ru.yandex.sbd.messenger.entities.Message;
import ru.yandex.sbd.messenger.exceptions.ChatIdNotFoundException;
import ru.yandex.sbd.messenger.exceptions.MessagesOffsetException;
import ru.yandex.sbd.messenger.exceptions.UserIdNotFoundException;
import ru.yandex.sbd.messenger.exceptions.UserNotInChatException;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.MessagesOut;
import ru.yandex.sbd.messenger.models.dto.ChatDto;
import ru.yandex.sbd.messenger.models.dto.MessageDto;
import ru.yandex.sbd.messenger.models.requestbody.ChatRequestBody;
import ru.yandex.sbd.messenger.models.requestbody.MessageRequestBody;
import ru.yandex.sbd.messenger.repositories.ChatsRepository;
import ru.yandex.sbd.messenger.repositories.MessagesRepository;
import ru.yandex.sbd.messenger.repositories.UsersRepository;
import ru.yandex.sbd.messenger.service.IChatsService;

import java.util.stream.Collectors;


@Service
public class ChatsService implements IChatsService {
    private final ChatsRepository chatsRepo;
    private final UsersRepository usersRepo;
    private final MessagesRepository messagesRepo;

    @Autowired
    public ChatsService(ChatsRepository chatsRepo, UsersRepository usersRepo, MessagesRepository messagesRepo) {
        this.chatsRepo = chatsRepo;
        this.usersRepo = usersRepo;
        this.messagesRepo = messagesRepo;
    }

    @Override
    public ChatDto createChat(ChatRequestBody chatBody) {
        Chat chat = new Chat(chatBody.getName());
        chatsRepo.save(chat);
        return new ChatDto(chat.getId());
    }

    @Override
    public boolean isDbConnected() {
        try {
            chatsRepo.isDbConnected();
            usersRepo.isDbConnected();
            messagesRepo.isDbConnected();
        } catch (Throwable ignored) {
            return false;
        }
        return true;
    }

    @Override
    @Cacheable("messages")
    public MessagesOut getMessagesFromChat(String chatId, String userId, int limit, Cursor cursor) {
        if (!chatsRepo.existsById(chatId)) {
            throw new ChatIdNotFoundException(String.format("Chat with id %s does not exist", chatId));
        }
        int offset = getOffset(cursor);
        if (offset > chatsRepo.getById(chatId).getMessageLog().size()) {
            throw new MessagesOffsetException("Cursor offset > messages count");
        }
        int messagesCount = getMessagesCount(chatId, limit, offset);
        var messages = chatsRepo.getById(chatId).getMessageLog().subList(offset,
                offset + messagesCount).stream().map(MessageDto::toDto).collect(Collectors.toList());
        int cursorOut = offset + messagesCount;
        return new MessagesOut(messages, cursorOut);
    }

    @Override
    public MessageDto sendMessageToChat(String chatId, String userId, MessageRequestBody messageBody) {
        if (!chatsRepo.existsById(chatId)) {
            throw new ChatIdNotFoundException(String.format("Chat with id %s does not exist", chatId));
        }
        if (!usersRepo.existsById(userId)) {
            throw new UserIdNotFoundException(String.format("User with id %s does not exist", userId));
        }
        if (!usersRepo.getById(userId).getChats().contains(chatsRepo.getById(chatId))) {
            throw new UserNotInChatException("No access for user " + userId);
        }
        Message message = new Message(messageBody.getMessage(), chatsRepo.getById(chatId), usersRepo.getById(userId));
        messagesRepo.save(message);
        chatsRepo.getById(chatId).getMessageLog().add(message);
        usersRepo.getById(userId).getMessages().add(message);
        chatsRepo.getById(chatId).getMessageLog().get(chatsRepo.getById(chatId).getMessageLog().size() - 1);
        return MessageDto.toDto(message);
    }

    private int getOffset(Cursor cursor) {
        return cursor != null ? Integer.parseInt(cursor.getIterator()) : 0;
    }


    private int getMessagesCount(String chatId, int limit, int offset) {
        if (!chatsRepo.existsById(chatId)) {
            throw new ChatIdNotFoundException(String.format("Chat with id %s does not exist", chatId));
        }
        return Math.min(limit, chatsRepo.getById(chatId).getMessageLog().size() - offset);
    }

}
