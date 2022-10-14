package ru.practicum.explorewithme.requests.service;

import ru.practicum.explorewithme.requests.dto.RequestDto;

import java.util.List;

public interface PrivateRequestService {
    List<RequestDto> getList(Long userId);

    RequestDto addItem(Long userId, Long eventId);

    RequestDto cancelItem(Long userId, Long requestId);

    List<RequestDto> getListForEvent(Long userId, Long eventId);

    RequestDto confirmItemForEvent(Long userId, Long eventId, Long reqId);

    RequestDto rejectItemForEvent(Long userId, Long eventId, Long reqId);
}
