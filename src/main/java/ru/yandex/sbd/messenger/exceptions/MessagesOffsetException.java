package ru.yandex.sbd.messenger.exceptions;

public class MessagesOffsetException extends RuntimeException {
    public MessagesOffsetException(String message) {
        super(message);
    }
}
