package ru.practicum.explorewithme.events.mapper;

import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.ModerationComment;
import ru.practicum.explorewithme.events.dto.ModerationCommentDto;
import ru.practicum.explorewithme.events.dto.NewModerationCommentDto;
import ru.practicum.explorewithme.users.User;
import ru.practicum.explorewithme.users.mapper.UserMapper;

public class ModerationCommentMapper {
    public static ModerationComment toModerationComment(
            Event event,
            NewModerationCommentDto newModerationCommentDto
    ) {
        ModerationComment moderationComment = new ModerationComment();
        moderationComment.setEvent(event);
        moderationComment.setDescription(newModerationCommentDto.getDescription());
        return moderationComment;
    }

    public static ModerationComment toModerationComment(
            Event event,
            NewModerationCommentDto newModerationCommentDto,
            User user
    ) {
        ModerationComment moderationComment = new ModerationComment();
        moderationComment.setEvent(event);
        moderationComment.setCommentator(user);
        moderationComment.setDescription(newModerationCommentDto.getDescription());
        return moderationComment;
    }

    public static ModerationCommentDto toModerationCommentDto(ModerationComment moderationComment) {
        return new ModerationCommentDto(
                moderationComment.getId(),
                UserMapper.toUserShortDto(moderationComment.getCommentator()),
                EventMapper.toEventShortDto(moderationComment.getEvent()),
                moderationComment.getDescription(),
                moderationComment.getCreatedAt()
        );
    }
}
