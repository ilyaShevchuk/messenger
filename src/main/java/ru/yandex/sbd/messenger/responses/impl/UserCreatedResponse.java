package ru.yandex.sbd.messenger.responses.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.sbd.messenger.responses.IResponse;

@Data
@AllArgsConstructor
public class UserCreatedResponse implements IResponse {
    @JsonProperty("user_id")
    private String id;
}
