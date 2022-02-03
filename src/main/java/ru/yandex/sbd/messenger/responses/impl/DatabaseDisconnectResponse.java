package ru.yandex.sbd.messenger.responses.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.sbd.messenger.responses.IResponse;

public class DatabaseDisconnectResponse implements IResponse {
    @Getter
    @Setter
    @JsonProperty("info")
    private String info;

    public DatabaseDisconnectResponse(String info) {
        this.info = info;
    }
}
