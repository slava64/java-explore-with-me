package ru.practicum.explorewithme.events.mapper;

import ru.practicum.explorewithme.categories.Category;
import ru.practicum.explorewithme.categories.mapper.CategoryMapper;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.State;
import ru.practicum.explorewithme.events.dto.*;
import ru.practicum.explorewithme.locations.Location;
import ru.practicum.explorewithme.locations.LocationMapper;
import ru.practicum.explorewithme.users.User;
import ru.practicum.explorewithme.users.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    public static EventDto toEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()),
                LocationMapper.toLocationDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static List<EventDto> toEventDto(List<Event> eventList) {
        List<EventDto> eventDtoList = new ArrayList<>();
        for (Event event : eventList) {
            eventDtoList.add(EventMapper.toEventDto(event));
        }
        return eventDtoList;
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static List<EventShortDto> toEventShortDto(List<Event> eventList) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Event event : eventList) {
            eventShortDtoList.add(EventMapper.toEventShortDto(event));
        }
        return eventShortDtoList;
    }


    public static Event toEvent(
            NewEventDto newEventDto,
            User initiator,
            Category category,
            Location location
    ) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setInitiator(initiator);
        event.setLocation(location);
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setTitle(newEventDto.getTitle());

        return event;
    }

    public static Event updatedEvent(Event event, UpdateEventDto updateEventDto, Category category) {
        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }
        if (updateEventDto.getEventDate() != null) {
            event.setEventDate(updateEventDto.getEventDate());
        }
        if (updateEventDto.getCategory() != null) {
            event.setCategory(category);
        }
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }
        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        event.setState(State.PENDING);
        return event;
    }

    public static Event updatedEvent(
            Event event,
            UpdateEventAdminDto updateEventAdminDto,
            Category category,
            Location location
    ) {
        if (updateEventAdminDto.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminDto.getAnnotation());
        }
        if (updateEventAdminDto.getCategory() != null) {
            event.setCategory(category);
        }
        if (updateEventAdminDto.getDescription() != null) {
            event.setDescription(updateEventAdminDto.getDescription());
        }
        if (updateEventAdminDto.getEventDate() != null) {
            event.setEventDate(updateEventAdminDto.getEventDate());
        }
        if (updateEventAdminDto.getLocation() != null) {
            event.setLocation(location);
        }
        if (updateEventAdminDto.getPaid() != null) {
            event.setPaid(updateEventAdminDto.getPaid());
        }
        if (updateEventAdminDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminDto.getParticipantLimit());
        }
        if (updateEventAdminDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminDto.getRequestModeration());
        }
        if (updateEventAdminDto.getTitle() != null) {
            event.setTitle(updateEventAdminDto.getTitle());
        }
        event.setState(State.PENDING);
        return event;
    }

}
