package net.lelyak.edu.service;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.repository.EventRepository;
import net.lelyak.edu.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Allows for admins
 * - to enter events,
 * - view purchased tickets.
 */
@Service
public class AdminService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository bookingRepository;

    public void enterEvent(Event event) {
        eventRepository.put(event);
    }

    public List<Event> listEvents() {
        return eventRepository.getAll();
    }

    public List<Ticket> listTickets() {
        return bookingRepository.getAll();
    }

}
