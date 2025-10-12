package net.lelyak.edu.service;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.UserAccount;
import net.lelyak.edu.repository.TicketRepository;
import net.lelyak.edu.repository.UserAccountRepository;
import net.lelyak.edu.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private UserAccountRepository userAccountRepository;

    public void saveTicket(Ticket ticket) {
        ticketRepository.put(ticket);
    }

    public Ticket bookTicket(Event event, Date date, String sits, User user) {
        double price = getTicketPrice(event, date, user);
        Ticket ticket = new Ticket();
        ticket.setName(sits);
        ticket.setEvent(event);
        ticket.setOnDate(date);
        ticket.setPrice(price);
        ticket.setUser(user);
        ticketRepository.put(ticket);
        return ticket;
    }

    @Transactional
    public boolean bookTicket(Ticket ticket) {
        Logger.info("Book ticket: " + ticket);
        double price = getTicketPrice(ticket.getEvent(), ticket.getOnDate(), ticket.getUser());
        ticket.setPrice(price);

        User customer = ticket.getUser();
        UserAccount account = userAccountRepository.getForUser(customer);
        double balance = account.getBalance();
        double totalPrice = ticket.getPrice();
        if (balance < totalPrice) {
            return false;
        }
        account.setBalance(balance - totalPrice);
        userAccountRepository.getDao().update(account);
        ticketRepository.put(ticket);
        return true;
    }

    public List<Ticket> getTicketsForUser(User user) {
        Logger.info("TicketService.getTicketsForUser");
        List<Ticket> tickets = ticketRepository.getAll().stream()
            .filter(Objects::nonNull)
            .filter(t -> Objects.equals(t.getUser().getId(), user.getId()))
            .collect(Collectors.toList());
        Logger.info("COLLECTED TICKETS: " + tickets);
        return tickets;
    }

    public Double getTicketPrice(Event event, Date date, User user) {
        Double finalPrice = event.getTicketPrice();

        double discount = discountService.getDiscount(user, event, date);
        if (discount != 0) {
            finalPrice = finalPrice * (1 - discount);
        }
        return finalPrice;
    }

    public List<Ticket> getTicketsForEvent(Event event) {
        return ticketRepository.getAll().stream()
            .filter(Objects::nonNull)
            .filter(t -> t.getEvent().equals(event))
            .collect(Collectors.toList());
    }

    public List<Ticket> getTicketsForEventDate(Event event, Date date) {
        return ticketRepository.getAll().stream()
            .filter(t -> t.getEvent().equals(event)
                && t.getOnDate().equals(date))
            .collect(Collectors.toList());
    }
}
