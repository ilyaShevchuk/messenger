package ru.yandex.sbd.messenger.responses.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.yandex.sbd.messenger.responses.IResponse;

public class DefaultErrorResponse implements IResponse {
    @JsonProperty("message")
    @Getter
    private final String message;

    public DefaultErrorResponse(String message) {
        this.message = message;
    }
}
