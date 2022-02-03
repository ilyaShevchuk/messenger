package ru.yandex.sbd.messenger.models.requestbody;

import lombok.Data;

@Data
public class AuthRequestBody {
    private String login;
    private String password;
}
