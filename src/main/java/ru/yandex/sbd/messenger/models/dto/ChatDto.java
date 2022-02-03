package ru.yandex.sbd.messenger.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class ChatDto {
    @Getter
    @JsonProperty("chat_id")
    private final String id;

    public ChatDto(String id) {
        this.id = id;
    }
}
