package ru.yandex.sbd.messenger.responses.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.sbd.messenger.responses.IResponse;

public class DatabaseConnectedResponse implements IResponse {
    @Getter
    @Setter
    @JsonProperty("info")
    private String info;

    public DatabaseConnectedResponse(String info) {
        this.info = info;
    }
}
