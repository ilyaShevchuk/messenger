package ru.yandex.sbd.messenger.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.sbd.messenger.controllers.ChatsController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ChatsController.class)
public class ChatsControllerTest {

}
