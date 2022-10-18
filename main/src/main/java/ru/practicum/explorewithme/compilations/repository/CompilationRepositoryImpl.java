package ru.practicum.explorewithme.compilations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.compilations.Compilation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompilationRepositoryImpl implements CompilationRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Compilation> getList(Integer from, Integer size, Boolean pinned) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Compilation> query = cb.createQuery(Compilation.class);
        Root<Compilation> compilationRoot = query.from(Compilation.class);

        List<Predicate> predicates = new ArrayList<>();
        if (pinned != null) {
            Path<Boolean> pinnedPath = compilationRoot.get("pinned");
            predicates.add(cb.equal(pinnedPath, pinned));
        }

        query.select(compilationRoot).where(predicates.toArray(new Predicate[0]));

        TypedQuery<Compilation> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }
}
