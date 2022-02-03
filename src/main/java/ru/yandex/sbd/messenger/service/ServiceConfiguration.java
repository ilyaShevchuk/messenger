package ru.yandex.sbd.messenger.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.yandex.sbd.messenger.repositories.ChatsRepository;
import ru.yandex.sbd.messenger.repositories.MessagesRepository;
import ru.yandex.sbd.messenger.repositories.UsersRepository;
import ru.yandex.sbd.messenger.service.impl.ChatsService;
import ru.yandex.sbd.messenger.service.impl.ChatsServiceProxy;

@Configuration
public class ServiceConfiguration {
    @Bean
    @Primary
    public IChatsService getServiceImpl(ChatsRepository chatsRepo, UsersRepository usersRepo, MessagesRepository messagesRepo) {
        return new ChatsService(chatsRepo, usersRepo, messagesRepo);
    }

    @Bean
    public IChatsService getServiceProxy(ChatsRepository chatsRepo, UsersRepository usersRepo, MessagesRepository messagesRepo) {
        return new ChatsServiceProxy(getServiceImpl(chatsRepo, usersRepo, messagesRepo));
    }
}
