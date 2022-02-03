package ru.yandex.sbd.messenger.exceptions.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.sbd.messenger.exceptions.*;
import ru.yandex.sbd.messenger.responses.IResponse;
import ru.yandex.sbd.messenger.responses.impl.DefaultErrorResponse;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler({MessagesOffsetException.class, BadParamsException.class, ConstraintViolationException.class,
            TaskNotFoundException.class, TaskNotCompletedException.class,  UserNotInChatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<IResponse> handleAndReturnBadRequest(Exception e) {
        return new ResponseEntity<>(new DefaultErrorResponse(Arrays.toString(e.getStackTrace())),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ChatIdNotFoundException.class, UserIdNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<IResponse> handleAndReturnNotFound(Exception e) {
        return new ResponseEntity<>(new DefaultErrorResponse(Arrays.toString(e.getStackTrace())),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({DatabaseConnectionException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<IResponse> handleAndReturnInternalServerError(Exception e) {
        return new ResponseEntity<>(new DefaultErrorResponse(Arrays.toString(e.getStackTrace())),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
