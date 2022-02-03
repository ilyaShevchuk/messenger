package ru.yandex.sbd.messenger.responses.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.sbd.messenger.responses.IResponse;

@Data
@AllArgsConstructor
public class GetHistoryResponse implements IResponse {
    @JsonProperty("task_id")
    private String taskId;
}
