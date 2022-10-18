package ru.practicum.explorewithme.events.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.Sort;
import ru.practicum.explorewithme.events.State;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Event> getList(
            Integer from,
            Integer size,
            List<Long> users,
            List<String> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();
        if (users != null) {
            predicates.add(root.get("initiator").in(users));
        }
        if (states != null) {
            List<State> stateList = new ArrayList<>();
            for (String state : states) {
                stateList.add(State.valueOf(state));
            }
            predicates.add(root.get("state").in(stateList));
        }
        if (categories != null) {
            predicates.add(root.get("category").in(categories));
        }
        if (rangeStart != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add((cb.lessThanOrEqualTo(root.get("eventDate"), rangeEnd)));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Event> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    @Override
    public List<Event> getPublicList(
            Integer from,
            Integer size,
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            String sort
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(event.get("state"), State.PUBLISHED));
        if (text != null) {
            predicates.add(
                    cb.or(
                            cb.like(cb.lower(event.get("annotation")), cb.lower(cb.literal("%" + text + "%"))),
                            cb.like(cb.lower(event.get("description")), cb.lower(cb.literal("%" + text + "%")))
                    )
            );
        }
        if (categories != null) {
            predicates.add(event.get("category").in(categories));
        }
        if (paid != null) {
            predicates.add(cb.equal(event.get("paid"), paid));
        }
        if (rangeStart != null) {
            predicates.add(cb.greaterThanOrEqualTo(event.get("eventDate"), rangeStart));
        } else {
            predicates.add(cb.greaterThanOrEqualTo(event.get("eventDate"), LocalDateTime.now()));
        }
        if (rangeEnd != null) {
            predicates.add((cb.lessThanOrEqualTo(event.get("eventDate"), rangeEnd)));
        }
        if (onlyAvailable != null && onlyAvailable) {
            predicates.add(cb.or(
                    cb.equal(event.get("participantLimit"), 0),
                    cb.lt(event.get("confirmedRequests"), event.get("participantLimit"))
                    ));
        }
        query.select(event).where(cb.and(predicates.toArray(new Predicate[0])));

        if (sort != null && sort.equals(Sort.EVENT_DATE.toString())) {
            query.orderBy(cb.asc(event.get("eventDate")));
        }
        if (sort != null && sort.equals(Sort.VIEWS.toString())) {
            query.orderBy(cb.asc(event.get("views")));
        }

        TypedQuery<Event> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    @Override
    public List<Event> getListStatusPending(Integer from, Integer size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("state"), State.PENDING));
        query.select(root).where(predicates.toArray(new Predicate[0]));

        TypedQuery<Event> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }
}
