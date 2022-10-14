package ru.practicum.explorewithme.compilations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.compilations.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long>, CompilationRepositoryCustom {
}
