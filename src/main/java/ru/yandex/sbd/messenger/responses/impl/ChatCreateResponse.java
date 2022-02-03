package ru.yandex.sbd.messenger.responses.impl;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.yandex.sbd.messenger.responses.IResponse;

public class ChatCreateResponse implements IResponse {
    @Getter
    @JsonProperty("chat_id")
    private final String chatId;

    public ChatCreateResponse(String chatId) {
        this.chatId = chatId;
    }
}
