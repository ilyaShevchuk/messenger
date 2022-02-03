package ru.yandex.sbd.messenger.exceptions;


public class ChatIdNotFoundException extends RuntimeException {
    public ChatIdNotFoundException(String message) {
        super(message);
    }
}
