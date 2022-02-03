package ru.yandex.sbd.messenger.responses.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.sbd.messenger.responses.IResponse;

@Data
@AllArgsConstructor
public class AuthResponse implements IResponse {

    private String token;
}
