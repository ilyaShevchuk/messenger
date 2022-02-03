package ru.yandex.sbd.messenger.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.sbd.messenger.responses.IResponse;
import ru.yandex.sbd.messenger.service.IChatsService;

@RestController
public class PingController {

    private final IChatsService chatsService;

    public PingController(IChatsService chatsService) {
        this.chatsService = chatsService;
    }

    @GetMapping
    public ResponseEntity<?> getStatus() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ping_db")
    public ResponseEntity<IResponse> dbPing() {
        return chatsService.isDbConnected() ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/ping")
    public ResponseEntity<IResponse> pingDb() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
