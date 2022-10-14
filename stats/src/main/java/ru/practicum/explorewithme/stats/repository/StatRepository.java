package ru.practicum.explorewithme.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.stats.Stat;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long>, StatRepositoryCustom {
}
