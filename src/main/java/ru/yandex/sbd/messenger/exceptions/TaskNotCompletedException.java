package ru.yandex.sbd.messenger.exceptions;

public class TaskNotCompletedException extends RuntimeException {
    public TaskNotCompletedException(String message) {
        super(message);
    }
}
