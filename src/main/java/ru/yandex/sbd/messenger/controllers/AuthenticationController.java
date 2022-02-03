package ru.yandex.sbd.messenger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.sbd.messenger.config.jwt.JwtProvider;
import ru.yandex.sbd.messenger.entities.User;
import ru.yandex.sbd.messenger.models.requestbody.AuthRequestBody;
import ru.yandex.sbd.messenger.models.requestbody.RegistrationRequestBody;
import ru.yandex.sbd.messenger.responses.IResponse;
import ru.yandex.sbd.messenger.responses.impl.AuthResponse;
import ru.yandex.sbd.messenger.responses.impl.DefaultErrorResponse;
import ru.yandex.sbd.messenger.responses.impl.UserCreatedResponse;
import ru.yandex.sbd.messenger.service.impl.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
public class AuthenticationController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthenticationController(UserService chatsService, JwtProvider jwtProvider) {
        this.userService = chatsService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<IResponse> registerUser(@RequestBody @Valid RegistrationRequestBody registrationRequestBody) {
        User user = new User("Ilya", "+3");
        user.setPassword(registrationRequestBody.getPassword());
        user.setLogin(registrationRequestBody.getLogin());
        var dto = userService.registerUser(user);
        return new ResponseEntity<>(new UserCreatedResponse(dto.getId()), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<IResponse> auth(@RequestBody AuthRequestBody request) {
        Optional<User> user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if (user.isPresent()) {
            String token = jwtProvider.generateToken(user.get().getLogin());
            return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(new DefaultErrorResponse("Can not auth"), HttpStatus.UNAUTHORIZED);
    }

}
