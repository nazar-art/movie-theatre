package net.lelyak.edu.discounts;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.service.TicketService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class HalfPriceTicketDiscount implements DiscountStrategy {

    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(HalfPriceTicketDiscount.class);

    private final static double DISCOUNT = .5;

    @Autowired
    private TicketService ticketService;

    @Override
    public double getDiscount(User user, Event event, Date date) {
        LOGGER.info("Every10TicketDiscountStrategy.getDiscount");

        List<Ticket> tickets = ticketService.getTicketsForUser(user);
        LOGGER.info("\ntickets = " + tickets);

        if (tickets == null || tickets.isEmpty()) {
            System.out.format("Every10TicketDiscountStrategy.getDiscount: no tickets for user '%s', event '%s'\n",
                user.getName(), event.getName());
            return 0;
        }

        if ((tickets.size() % 10) == 9) {
            return DISCOUNT;
        }
        return 0;
    }
}
