package net.lelyak.edu.repository;

import net.lelyak.edu.dao.impl.EventDAO;
import net.lelyak.edu.entity.Auditorium;
import net.lelyak.edu.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class EventRepository extends BaseRepository<Event, EventDAO> {

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @Override
    public long put(Event entity) {
        return super.put(entity);
    }

    @Override
    public Event getById(long id) {
        return initEventAuditorium(super.getById(id));
    }

    @Override
    public Event getByName(String name) {
        return initEventAuditorium(super.getByName(name));
    }

    @Override
    public List<Event> getAll() {
        return super.getAll();
    }

    @Override
    public void putAll(Collection<Event> entities) {
        entities.stream()
            .map(Event::getAuditorium)
            .filter(Objects::nonNull)
            .filter(a -> a.getId() != null)
            .forEach(auditoriumRepository::put);
        super.putAll(entities);
    }

    private Event initEventAuditorium(Event event) {
        if (event != null) {
            if (event.getAuditorium_id() != null) {
                event.setAuditorium(auditoriumRepository.getById(event.getAuditorium_id()));
            }
        }
        return event;
    }

    private Event saveEventAuditorium(Event event) {
        if (event != null && event.getAuditorium() != null) {
            Auditorium auditorium = event.getAuditorium();
            if (auditorium.getId() == null) {
                auditoriumRepository.put(auditorium);
            }
            event.setAuditorium_id(event.getAuditorium().getId());
        }
        return event;
    }

    @Override
    public Event preSave(Event entity) {
        return saveEventAuditorium(entity);
    }

    @Override
    public Event postLoad(Event entity) {
        return initEventAuditorium(entity);
    }

    public List<Event> getForDateRange(Date from, Date to) {
        return getDao().getAll().stream()
            .filter(e -> e.getAirDate().after(from) && e.getAirDate().before(to))
            .collect(Collectors.toList());
    }

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        if (event != null && auditorium != null) {
            if (auditorium.getId() == null) {
                auditoriumRepository.put(auditorium);
            }
            event.setAuditorium(auditorium);
            event.setAuditorium_id(auditorium.getId());

            if (date != null && !event.getAirDate().after(date)) {
                throw new IllegalArgumentException("Event airDate must be after assignment date: " + date);
            }
        }
    }

    public List<Event> getNextEvents(Date to) {
        Date now = new Date();
        return getDao().getAll().stream()
            .filter(e -> e.getAirDate().before(to) && e.getAirDate().after(now))
            .collect(Collectors.toList());
    }
}
