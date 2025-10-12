package net.lelyak.edu.tests.aspects;

import net.lelyak.edu.BaseTest;
import net.lelyak.edu.aspects.DiscountAspect.Record;
import net.lelyak.edu.discounts.IDiscountStrategy;
import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.utils.Logger;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;


public class DiscountAspectTestCase extends BaseTest {

    @Test // todo fix test logic for this test
    public void testAfterDiscountStrategyGetDiscount() {
        User user = randomTestGenerator.randomUser();
        Event event = randomTestGenerator.randomEvent();
        String sits = randomTestGenerator.getRandomSentence();

        userRepository.put(user);
        event.setAuditorium(auditoriumService.getRandomAuditorium());
        eventRepository.put(event);

        Logger.info("testAfterDiscountStrategyGetDiscount");
        //birthday strategy - 1 time
        Logger.info("birthdayDiscountStrategy, discount = " + ticketService.getTicketPrice(event, user.getBirthday(), user));

        //10 ticket strategy
        for (int i = 0; i < 9; i++) {
            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setUser(user);
            ticket.setPrice(event.getTicketPrice());
            ticket.setOnDate(event.getAirDate());
            ticket.setName(sits);
            ticketRepository.put(ticket);
            Logger.info("Adding ticket to ticketRepository " + ticket);
        }

        Logger.info("every10TicketDiscountStrategy, discount = " + ticketService.getTicketPrice(event, event.getAirDate(), user));

        Map<IDiscountStrategy, Record> discountCounter = discountAspect.getDiscountCounter();
        Logger.info("DISCOUNT_COUNTER:");

        long sum = 0;
        for (IDiscountStrategy ds : discountCounter.keySet()) {
            Record record = discountCounter.get(ds);
            Logger.info("  Strategy " + ds.getClass().getName() + "  totalCount=" + record.getTotalCount());
            sum += record.getTotalCount();
            for (User u : record.getCountPerUser().keySet()) {
                Logger.debug("    for user " + u.getName() + ": " + record.getCountPerUser().get(u));
                sum += record.getCountPerUser().get(u);
            }
        }
        Logger.info("Summa = " + sum);
        assertEquals(sum, 4, "summa isn't as expected");
    }

}
