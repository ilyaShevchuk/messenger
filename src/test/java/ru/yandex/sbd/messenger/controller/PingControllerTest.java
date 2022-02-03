package ru.yandex.sbd.messenger.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.sbd.messenger.controllers.PingController;
import ru.yandex.sbd.messenger.service.impl.ChatsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PingController.class)
@EnableAutoConfiguration(exclude=LiquibaseAutoConfiguration.class)
class PingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ChatsService chatsService;

    @Test
    public void givenPing_whenAppAvailable_thenOk() throws Exception {
        mvc.perform(get("/ping")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenPingDb_whenAvailableDb_thenOk() throws Exception {
        Mockito.when(chatsService.isDbConnected())
                .thenReturn(true);

        mvc.perform(get("/ping_db")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenPingDb_whenNotAvailableDb_thenError() throws Exception {
        Mockito.when(chatsService.isDbConnected())
                .thenReturn(false);

        mvc.perform(get("/ping_db")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable());
    }
}
