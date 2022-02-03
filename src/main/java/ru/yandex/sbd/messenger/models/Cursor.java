package ru.yandex.sbd.messenger.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Component
public class Cursor implements Serializable {
    @Getter
    @Setter
    @JsonProperty("iterator")
    private String iterator;
}
