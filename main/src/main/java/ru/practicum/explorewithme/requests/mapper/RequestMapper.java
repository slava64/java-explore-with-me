package ru.practicum.explorewithme.requests.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.requests.Request;
import ru.practicum.explorewithme.requests.Status;
import ru.practicum.explorewithme.requests.dto.RequestDto;
import ru.practicum.explorewithme.users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {
    public static Request createRequest(User user, Event event, Status status) {
        Request request = new Request();
        request.setStatus(status);
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        return request;
    }

    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus().toString()
        );
    }

    public static List<RequestDto> toRequestDto(List<Request> requestList) {
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requestList) {
            requestDtoList.add(RequestMapper.toRequestDto(request));
        }
        return requestDtoList;
    }

}
