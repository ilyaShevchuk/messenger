package ru.yandex.sbd.messenger.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.Id;


public class UserDto {
    @Id
    @Getter
    @JsonProperty("user_id")
    private final String id;

    public UserDto(String id) {
        this.id = id;
    }
}
