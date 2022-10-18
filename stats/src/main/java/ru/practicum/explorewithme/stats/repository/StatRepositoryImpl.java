package ru.practicum.explorewithme.stats.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.stats.Stat;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatRepositoryImpl implements StatRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Object[]> getList(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Stat> stat = query.from(Stat.class);

        List<Predicate> predicates = new ArrayList<>();
        if (start != null) {
            predicates.add(cb.greaterThanOrEqualTo(stat.get("timestamp"), start));
        }
        if (end != null) {
            predicates.add((cb.lessThanOrEqualTo(stat.get("timestamp"), end)));
        }
        if (uris != null) {
            predicates.add(stat.get("uri").in(uris));
        }
        query.groupBy(stat.get("uri"), stat.get("app"));
        query.multiselect(
                stat.get("app"),
                stat.get("uri"),
                unique != null && unique ? cb.countDistinct(stat.get("ip")) : cb.count(stat.get("ip"))
        );
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Object[]> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
