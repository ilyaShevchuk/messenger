package ru.yandex.sbd.messenger.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.sbd.messenger.models.dto.MessageDto;

import java.util.List;

@AllArgsConstructor
public class MessagesOut {

    @Getter
    private final List<MessageDto> messagesDtoList;

    @Getter
    private final int cursor;
}
