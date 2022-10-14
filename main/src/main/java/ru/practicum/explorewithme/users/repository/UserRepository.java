package ru.practicum.explorewithme.users.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.users.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Поиск пользователя по email без учета регистра
    Optional<User> findFirstByEmailContainingIgnoreCase(String email);

    // Получить список пользователей по списку id
    List<User> findByIdIn(List<Long> ids, Pageable pageable);
}
