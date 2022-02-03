package ru.yandex.sbd.messenger.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.yandex.sbd.messenger.entities.FoundMessage;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class HistoryMessageDto {
    @Id
    @Getter
    @JsonProperty("chat_id")
    @NotNull
    private final String chatId;

    @Getter
    @JsonProperty("text")
    @NotNull
    private final String text;


    public HistoryMessageDto(String text, String chatId) {
        this.chatId = chatId;
        this.text = text;
    }

    public static HistoryMessageDto toDto(FoundMessage entity) {
        return new HistoryMessageDto(entity.getText(), entity.getChatId());
    }
}