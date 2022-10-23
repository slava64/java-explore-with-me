package ru.practicum.explorewithme.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.categories.Category;
import ru.practicum.explorewithme.categories.repository.CategoryRepository;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.State;
import ru.practicum.explorewithme.events.dto.EventDto;
import ru.practicum.explorewithme.events.dto.EventShortDto;
import ru.practicum.explorewithme.events.dto.NewEventDto;
import ru.practicum.explorewithme.events.dto.UpdateEventDto;
import ru.practicum.explorewithme.events.mapper.EventMapper;
import ru.practicum.explorewithme.events.repository.EventRepository;
import ru.practicum.explorewithme.exception.BadRequestException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.locations.Location;
import ru.practicum.explorewithme.locations.LocationDto;
import ru.practicum.explorewithme.locations.LocationMapper;
import ru.practicum.explorewithme.locations.LocationRepository;
import ru.practicum.explorewithme.users.User;
import ru.practicum.explorewithme.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<EventShortDto> getList(Long userId, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Pageable pageable = PageRequest.of((int) from / size, size);
        List<Event> eventList = eventRepository.findByInitiatorId(userId, pageable);
        return EventMapper.toEventShortDto(eventList);
    }

    @Override
    public EventDto updateItem(Long userId, UpdateEventDto updateEventDto) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Event event = eventRepository.findById(updateEventDto.getEventId()).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        if (event.getState().equals(State.PUBLISHED)) {
            throw new BadRequestException("Событие опубликованное, его менять нельзя");
        }
        Category category = null;
        if (updateEventDto.getCategory() != null) {
            category = categoryRepository.findById(updateEventDto.getCategory()).orElseThrow(
                    () -> new NotFoundException("Категория не найдена")
            );
        }
        Event updatedEvent = EventMapper.updatedEvent(event, updateEventDto, category);
        if (updatedEvent.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException("Дата события должна быть объявлена не раньше чем за два часа");
        }
        Event savedEvent = eventRepository.save(updatedEvent);
        return EventMapper.toEventDto(savedEvent);
    }

    @Override
    public EventDto addItem(Long userId, NewEventDto newEventDto) {
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(
                () -> new NotFoundException("Категория не найдена")
        );
        Location location = getLocation(newEventDto.getLocation());
        Event event = EventMapper.toEvent(newEventDto, initiator, category, location);
        Event savedEvent = eventRepository.save(event);
        return EventMapper.toEventDto(savedEvent);
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
    public EventDto getItem(Long userId, Long eventId) {
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        return EventMapper.toEventDto(event);
    }

    @Override
    public EventDto cancelItem(Long userId, Long eventId) {
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено")
        );
        if (event.getState().equals(State.PUBLISHED)) {
            throw new BadRequestException("Отменить событие можно только в состоянии ожидания модерации");
        }
        event.setState(State.CANCELED);
        Event cancelEvent = eventRepository.save(event);
        return EventMapper.toEventDto(cancelEvent);
    }
}
