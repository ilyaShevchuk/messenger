package ru.yandex.sbd.messenger.responses.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.dto.MessageDto;
import ru.yandex.sbd.messenger.responses.IResponse;

import java.util.List;

public class ChatGetMessagesResponse implements IResponse {
    @JsonProperty("messages")
    @Getter
    private final List<MessageDto> messages;
    @Getter
    @JsonProperty("next")
    private final Cursor next;

    public ChatGetMessagesResponse(List<MessageDto> messages, int next) {
        this.messages = messages;
        this.next = new Cursor(String.valueOf(next));
    }
}
