package ru.practicum.explorewithme.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.categories.Category;
import ru.practicum.explorewithme.categories.repository.CategoryRepository;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.ModerationComment;
import ru.practicum.explorewithme.events.State;
import ru.practicum.explorewithme.events.dto.EventDto;
import ru.practicum.explorewithme.events.dto.ModerationCommentDto;
import ru.practicum.explorewithme.events.dto.NewModerationCommentDto;
import ru.practicum.explorewithme.events.dto.UpdateEventAdminDto;
import ru.practicum.explorewithme.events.mapper.EventMapper;
import ru.practicum.explorewithme.events.mapper.ModerationCommentMapper;
import ru.practicum.explorewithme.events.repository.EventRepository;
import ru.practicum.explorewithme.events.repository.ModerationCommentRepository;
import ru.practicum.explorewithme.exception.BadRequestException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.locations.Location;
import ru.practicum.explorewithme.locations.LocationDto;
import ru.practicum.explorewithme.locations.LocationMapper;
import ru.practicum.explorewithme.locations.LocationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final ModerationCommentRepository moderationCommentRepository;

    @Override
    public List<EventDto> getList(
            Integer from,
            Integer size,
            List<Long> users,
            List<String> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd
    ) {
        List<Event> eventList = eventRepository.getList(from, size, users, states, categories, rangeStart, rangeEnd);
        return EventMapper.toEventDto(eventList);
    }

    @Override
    public List<EventDto> getListStatusPending(Integer from, Integer size) {
        List<Event> eventList = eventRepository.getListStatusPending(from, size);
        return EventMapper.toEventDto(eventList);
    }

    @Override
    public EventDto editItem(Long eventId, UpdateEventAdminDto updateEventAdminDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        Category category = null;
        if (updateEventAdminDto.getCategory() != null) {
            category = categoryRepository.findById(updateEventAdminDto.getCategory()).orElseThrow(
                    () -> new NotFoundException("Категория не найдена")
            );
        }
        Location location = null;
        if (updateEventAdminDto.getLocation() != null) {
            location = getLocation(updateEventAdminDto.getLocation());
        }

        Event updatedEvent = EventMapper.updatedEvent(event, updateEventAdminDto, category, location);
        return EventMapper.toEventDto(updatedEvent);
    }

    // Если есть локация, то возвращает, если нету, то сохраняет её
    private Location getLocation(LocationDto locationDto) {
        Optional<Location> location = locationRepository
                .findFirstByLatAndLon(locationDto.getLat(), locationDto.getLon());
        if (location.isEmpty()) {
            return locationRepository.save(LocationMapper.toLocation(locationDto));
        }
        return location.get();
    }

    @Override
    public EventDto setPublish(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        LocalDateTime datePublished = LocalDateTime.now();
        if (event.getEventDate().isBefore(datePublished.plusHours(1))) {
            throw new BadRequestException("Дата начала события должна быть не " +
                    "ранее чем за час от даты публикации.");
        }
        event.setPublishedOn(datePublished);
        event.setState(State.PUBLISHED);
        Event updatedEvent = eventRepository.save(event);
        return EventMapper.toEventDto(updatedEvent);
    }

    @Override
    public EventDto setReject(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        if (event.getState().equals(State.PUBLISHED)) {
            throw new BadRequestException("Событие не должно быть опубликовано");
        }
        event.setState(State.CANCELED);
        Event updatedEvent = eventRepository.save(event);
        return EventMapper.toEventDto(updatedEvent);
    }

    @Override
    public ModerationCommentDto addModerationComment(Long eventId, NewModerationCommentDto newModerationCommentDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        ModerationComment comment = ModerationCommentMapper.toModerationComment(
                event, newModerationCommentDto);
        ModerationComment savedComment = moderationCommentRepository.save(comment);
        event.setState(State.CANCELED);
        eventRepository.save(event);
        return ModerationCommentMapper.toModerationCommentDto(savedComment);
    }
}
