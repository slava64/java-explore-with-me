package ru.practicum.explorewithme.requests.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.requests.dto.RequestDto;
import ru.practicum.explorewithme.requests.service.PrivateRequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PrivateRequestController {
    private final PrivateRequestService privateRequestService;

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getList(@Positive @PathVariable("userId") Long userId) {
        log.info("List requests for user {}", userId);
        return privateRequestService.getList(userId);
    }

    @PostMapping("/{userId}/requests")
    public RequestDto addItem(
            @Positive @PathVariable("userId") Long userId,
            @Positive @RequestParam(value = "eventId") Long eventId
    ) {
        log.info("Add request for event {} for user {}", eventId, userId);
        return privateRequestService.addItem(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelItem(
            @Positive @PathVariable("userId") Long userId,
            @Positive @PathVariable("requestId") Long requestId
    ) {
        log.info("Cancel request {} for user {}", requestId, userId);
        return privateRequestService.cancelItem(userId, requestId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<RequestDto> getListForEvent(
            @Positive @PathVariable("userId") Long userId,
            @Positive @PathVariable("eventId") Long eventId) {
        log.info("List requests for user {} for event {}", userId, eventId);
        return privateRequestService.getListForEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmItemForEvent(
            @Positive @PathVariable("userId") Long userId,
            @Positive @PathVariable("eventId") Long eventId,
            @Positive @PathVariable("reqId") Long reqId) {
        log.info("Confirm request {} for user {} for event {}", reqId, userId, eventId);
        return privateRequestService.confirmItemForEvent(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectItemForEvent(
            @Positive @PathVariable("userId") Long userId,
            @Positive @PathVariable("eventId") Long eventId,
            @Positive @PathVariable("reqId") Long reqId) {
        log.info("Reject request {} for user {} for event {}", reqId, userId, eventId);
        return privateRequestService.rejectItemForEvent(userId, eventId, reqId);
    }
}
