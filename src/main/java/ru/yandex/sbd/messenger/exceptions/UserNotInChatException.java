package ru.yandex.sbd.messenger.exceptions;

public class UserNotInChatException extends RuntimeException {
    public UserNotInChatException(String message) {
        super(message);}
}