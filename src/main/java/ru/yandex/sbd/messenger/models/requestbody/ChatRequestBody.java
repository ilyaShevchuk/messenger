package ru.yandex.sbd.messenger.models.requestbody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@AllArgsConstructor
public class ChatRequestBody {
    @Getter
    @JsonProperty("chat_name")
    @Size(min = 1, max = 255)
    @NotNull
    private String name;

}
