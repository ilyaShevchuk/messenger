package ru.yandex.sbd.messenger.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.sbd.messenger.service.impl.ChatsService;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DbConnectionTest {

    @Mock
    DataSource dataSource;

    @InjectMocks
    ChatsService chatsService;

    @Test
    public void Should_DbIsAvailable_When_GetConnectionIsValid() throws SQLException {
        when(dataSource.getConnection()).
                thenReturn(null);

        boolean isActiveDb = chatsService.isDbConnected();

        Assertions.assertThat(isActiveDb).isEqualTo(true);
    }

    @Test
    public void Should_DbIsAvailable_When_GetConnectionThrowException() throws SQLException {
        when(dataSource.getConnection())
                .thenThrow(SQLException.class);

        boolean isActiveDb = chatsService.isDbConnected();

        Assertions.assertThat(isActiveDb).isEqualTo(false);
    }
}
