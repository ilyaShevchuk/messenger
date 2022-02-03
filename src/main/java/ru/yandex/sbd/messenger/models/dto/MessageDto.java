package ru.yandex.sbd.messenger.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.yandex.sbd.messenger.entities.Message;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class MessageDto {
    @Id
    @Getter
    @JsonProperty("id")
    @NotNull
    private final String id;

    @Getter
    @JsonProperty("text")
    @NotNull
    private final String text;


    public MessageDto(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public static MessageDto toDto(Message entity) {
        return new MessageDto(entity.getId(), entity.getText());
    }
}
