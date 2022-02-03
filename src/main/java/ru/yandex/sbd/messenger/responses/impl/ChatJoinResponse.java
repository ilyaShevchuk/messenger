package ru.yandex.sbd.messenger.responses.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.yandex.sbd.messenger.responses.IResponse;

public class ChatJoinResponse implements IResponse {
    @Getter
    @JsonProperty("user_id")
    private final String userId;

    public ChatJoinResponse(String userId) {
        this.userId = userId;
    }
}
