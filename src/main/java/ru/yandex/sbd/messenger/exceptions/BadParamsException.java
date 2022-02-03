package ru.yandex.sbd.messenger.exceptions;

public class BadParamsException extends RuntimeException {
    public BadParamsException(String chat_name_is_null) {
        super(chat_name_is_null);
    }
}
