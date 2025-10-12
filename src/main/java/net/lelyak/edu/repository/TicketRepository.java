package net.lelyak.edu.repository;

import net.lelyak.edu.dao.impl.TicketDAO;
import net.lelyak.edu.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketRepository extends BaseRepository<Ticket, TicketDAO> {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Ticket preSave(Ticket entity) {
        return saveTicketUser(saveTicketEvent(entity));
    }

    @Override
    public Ticket postLoad(Ticket entity) {
        return initTicketUser(initTicketEvent(entity));
    }

    private Ticket initTicketEvent(Ticket entity) {
        if (entity != null) {
            if (entity.getEvent_id() != null) {
                entity.setEvent(eventRepository.getById(entity.getEvent_id()));
            }
        }
        return entity;
    }

    private Ticket saveTicketEvent(Ticket entity) {
        if (entity != null) {
            if (entity.getEvent() != null) {
                eventRepository.put(entity.getEvent());
                entity.setEvent_id(entity.getEvent().getId());
            }
        }
        return entity;
    }

    private Ticket initTicketUser(Ticket entity) {
        if (entity != null) {
            if (entity.getUser_id() != null) {
                entity.setUser(userRepository.getById(entity.getUser_id()));
            }
        }
        return entity;
    }

    private Ticket saveTicketUser(Ticket entity) {
        if (entity != null) {
            if (entity.getUser() != null) {
                userRepository.put(entity.getUser());
                entity.setUser_id(entity.getUser().getId());
            }
        }
        return entity;
    }
}
