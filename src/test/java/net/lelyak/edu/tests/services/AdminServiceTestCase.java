package net.lelyak.edu.tests.services;

import net.lelyak.edu.BaseTest;
import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.utils.StringUtilities;
import org.apache.log4j.LogManager;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertTrue;


@Transactional
public class AdminServiceTestCase extends BaseTest {

    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(AdminServiceTestCase.class);

    private final Random random = new Random();

    private List<Event> randomEvents() {
        List<Event> testEvents = randomTestGenerator.randomEventsList(5 + random.nextInt(10));
        for (Event event : testEvents) {
            event.setAuditorium(auditoriumService.getRandomAuditorium());
        }
        return testEvents;
    }

    @Test
    public void testEnterEvent() throws Exception {
        List<Event> testEvents = randomEvents();
        Collection<Event> loadedEvents;
        for (Event event : testEvents) {
            adminService.enterEvent(event);
        }
        loadedEvents = adminService.listEvents();
        LOGGER.debug("============== testEnterEvent ==============================");
        for (Event event : loadedEvents) {
            LOGGER.debug(StringUtilities.appendStrings(" testEnterEvent %s", event));
        }

        assertTrue(loadedEvents.containsAll(testEvents), "doesn't contains all events");
    }

    @Test
    public void testListTickets() {
        List<User> testUsers = randomTestGenerator.randomUserList(5 + random.nextInt(10));
        List<Event> testEvents = randomEvents();
        Collection<Ticket> ticketRepositoryTickets, adminServiceTickets;

        userRepository.putAll(testUsers);

        eventRepository.putAll(testEvents);

        for (int i = 0; i < 5 + random.nextInt(10); i++) {
            //    public Ticket bookTicket ( Event event, Date date, String sits, User user )
            Event event = testEvents.get(random.nextInt(testEvents.size()));
            User user = testUsers.get(random.nextInt(testUsers.size()));

            Ticket ticket = new Ticket();
            ticket.setOnDate(event.getAirDate());
            ticket.setEvent(event);
            ticket.setName(randomTestGenerator.getRandomWord());
            ticket.setPrice(event.getTicketPrice());
            ticket.setUser(user);
            ticketRepository.put(ticket);
        }

        ticketRepositoryTickets = ticketRepository.getAll();
        adminServiceTickets = adminService.listTickets();

        LOGGER.debug("============== testListTickets ==============================");
        for (Ticket ticket : adminServiceTickets) {
            LOGGER.debug(StringUtilities.appendStrings("testListTickets : %s", ticket));
        }

        assertTrue(adminServiceTickets.containsAll(ticketRepositoryTickets)
            && adminServiceTickets.size() == ticketRepositoryTickets.size());

    }
}