package ru.yandex.sbd.messenger.models.requestbody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.sbd.messenger.models.dto.HistoryMessageDto;

import java.util.List;

@AllArgsConstructor
public class HistoryMessagesOut {
    @Getter
    private final List<HistoryMessageDto> messagesDtoList;

    @Getter
    private final int cursor;
}
