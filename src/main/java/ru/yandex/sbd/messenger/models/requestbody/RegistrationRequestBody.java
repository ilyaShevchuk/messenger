package ru.yandex.sbd.messenger.models.requestbody;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequestBody {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;
}
