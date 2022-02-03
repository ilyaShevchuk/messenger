package ru.yandex.sbd.messenger.responses.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.sbd.messenger.models.Cursor;
import ru.yandex.sbd.messenger.models.dto.HistoryMessageDto;
import ru.yandex.sbd.messenger.responses.IResponse;

import java.util.List;

@Data
@AllArgsConstructor
public class HistoryGetMessagesResponse implements IResponse {

    private List<HistoryMessageDto> messages;

    private Cursor next;
}
