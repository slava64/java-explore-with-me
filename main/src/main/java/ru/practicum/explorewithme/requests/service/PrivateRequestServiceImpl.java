package ru.practicum.explorewithme.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.State;
import ru.practicum.explorewithme.events.repository.EventRepository;
import ru.practicum.explorewithme.exception.BadRequestException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.requests.Request;
import ru.practicum.explorewithme.requests.Status;
import ru.practicum.explorewithme.requests.dto.RequestDto;
import ru.practicum.explorewithme.requests.mapper.RequestMapper;
import ru.practicum.explorewithme.requests.repository.RequestRepository;
import ru.practicum.explorewithme.users.User;
import ru.practicum.explorewithme.users.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<RequestDto> getList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        return RequestMapper.toRequestDto(requestRepository.findByRequesterId(user.getId()));
    }

    @Override
    @Transactional
    public RequestDto addItem(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        if (user.equals(event.getInitiator())) {
            throw new BadRequestException("Инициатор события не может добавить " +
                    "запрос на участие в своём событии");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new BadRequestException("Нельзя участвовать в не опубликованном событии");
        }
        if (event.getParticipantLimit() != 0
                && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new BadRequestException("У события достигнут лимит запросов на участие");
        }
        Request request;
        if (event.getRequestModeration()) {
            request = RequestMapper.createRequest(user, event, Status.PENDING);
        } else {
            request = RequestMapper.createRequest(user, event, Status.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelItem(Long userId, Long requestId) {
        Request request = requestRepository.findFirstByIdAndRequesterId(requestId, userId);
        if (request == null) {
            throw new BadRequestException("Запрос не найден");
        }
        if (request.getStatus().equals(Status.CONFIRMED)) {
            throw new BadRequestException("Запрос уже подтвержден");
        }
        request.setStatus(Status.CANCELED);
        Request savedRequest = requestRepository.save(request);
        return RequestMapper.toRequestDto(savedRequest);
    }

    @Override
    public List<RequestDto> getListForEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        if (!user.equals(event.getInitiator())) {
            throw new BadRequestException("Событие не пренадлежит пользователю");
        }
        List<Request> requestList = requestRepository.findByEventId(eventId);
        return RequestMapper.toRequestDto(requestList);
    }

    @Override
    @Transactional
    public RequestDto confirmItemForEvent(Long userId, Long eventId, Long reqId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        if (!user.equals(event.getInitiator())) {
            throw new BadRequestException("Событие не пренадлежит пользователю");
        }
        Request request = requestRepository.findById(reqId).orElseThrow(
                () -> new NotFoundException("Запрос не найден")
        );
        if (!request.getEvent().equals(event)) {
            throw new BadRequestException("Запрос не пренадлежит событию");
        }
        if (event.getParticipantLimit() != 0
                && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new BadRequestException("У события достигнут лимит запросов на участие");
        }
        int countConfirmedRequests = event.getConfirmedRequests() + 1;
        request.setStatus(Status.CONFIRMED);
        event.setConfirmedRequests(countConfirmedRequests);
        eventRepository.save(event);
        Request savedRequest1 = requestRepository.save(request);

        if (countConfirmedRequests >= event.getParticipantLimit()) {
            List<Request> requestList = requestRepository.findByEventIdAndStatus(eventId, Status.PENDING);
            for (Request request1 : requestList) {
                if (!request1.equals(request)) {
                    request1.setStatus(Status.REJECTED);
                    requestRepository.save(request1);
                }
            }
        }

        return RequestMapper.toRequestDto(savedRequest1);
    }

    @Override
    public RequestDto rejectItemForEvent(Long userId, Long eventId, Long reqId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        if (!user.equals(event.getInitiator())) {
            throw new BadRequestException("Событие не пренадлежит пользователю");
        }
        Request request = requestRepository.findById(reqId).orElseThrow(
                () -> new NotFoundException("Запрос не найден")
        );
        if (!request.getEvent().equals(event)) {
            throw new BadRequestException("Запрос не пренадлежит событию");
        }
        if (request.getStatus().equals(Status.CONFIRMED)) {
            throw new BadRequestException("Запрос уже подтвержден");
        }
        request.setStatus(Status.REJECTED);
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }
}
