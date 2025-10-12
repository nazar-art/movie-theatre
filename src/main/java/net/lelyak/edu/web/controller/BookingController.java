package net.lelyak.edu.web.controller;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.repository.EventRepository;
import net.lelyak.edu.service.AuditoriumService;
import net.lelyak.edu.service.TicketService;
import net.lelyak.edu.service.UserService;
import net.lelyak.edu.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("ticket")
public class BookingController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuditoriumService auditoriumService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(value = "/book/{id}", method = RequestMethod.POST)
    public String bookPlaceForEvent(
        @PathVariable("id") long id,
        @RequestParam("targetSeats") List<String> chosenSeatsStrings) {
        Logger.info("Chosen seats string: " + chosenSeatsStrings.toString());

        User customer = userService.getAll().get(0);
        Event targetEvent = eventRepository.getById(id);

        Long[] chosenSeats = new Long[chosenSeatsStrings.size()];
        Logger.info("Chosen seats string: " + Arrays.toString(chosenSeats));
        for (int i = 0; i < chosenSeatsStrings.size(); i++) {
            chosenSeats[i] = Long.parseLong(chosenSeatsStrings.get(i));
        }

        Ticket ticket = new Ticket();
        ticket.setEvent(targetEvent);
        ticket.setUser(customer);
        String seatNumber = Arrays.toString(chosenSeats);
        ticket.setName(seatNumber);
        ticketService.bookTicket(targetEvent, targetEvent.getAirDate(), seatNumber, customer);
        return "redirect:/tickets/my";
    }

}
