package ru.yandex.sbd.messenger.models.requestbody;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateSearchTaskBody {

    @Getter
    @NotNull
    @Size(min = 3)
    @JsonProperty("message")
    private String message;

    @JsonIgnore
    public boolean isValid() {
        return message.length() > 3;
    }

}
