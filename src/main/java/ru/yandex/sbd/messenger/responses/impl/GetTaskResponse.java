package ru.yandex.sbd.messenger.responses.impl;

import lombok.Data;
import ru.yandex.sbd.messenger.responses.IResponse;

@Data
public class GetTaskResponse implements IResponse {
    private String status;

    public GetTaskResponse(String status) {
        this.status = status;
    }
}
