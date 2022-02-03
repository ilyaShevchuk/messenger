package ru.yandex.sbd.messenger.responses.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.yandex.sbd.messenger.models.dto.MessageDto;
import ru.yandex.sbd.messenger.responses.IResponse;

public class ChatSendMessageResponse implements IResponse {
    @JsonProperty("message_id")
    @Getter
    private final String messageId;

    public ChatSendMessageResponse(MessageDto message) {
        this.messageId = message.getId();
    }
}
