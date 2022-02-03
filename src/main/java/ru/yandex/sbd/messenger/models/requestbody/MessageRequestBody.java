package ru.yandex.sbd.messenger.models.requestbody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
@AllArgsConstructor
public class MessageRequestBody {
    @Getter
    @JsonProperty("message")
    @NotNull
    private String message;
}
