package ru.practicum.explorewithme.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.events.ModerationComment;

public interface ModerationCommentRepository extends JpaRepository<ModerationComment, Long> {
}
