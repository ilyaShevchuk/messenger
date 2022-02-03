package ru.yandex.sbd.messenger.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TaskDto {

    @JsonProperty("task_id")
    private Long taskId;

    public TaskDto(Long id) {
        this.taskId = id;
    }
}
