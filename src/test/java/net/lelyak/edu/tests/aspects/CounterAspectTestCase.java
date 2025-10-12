package net.lelyak.edu.tests.aspects;

import net.lelyak.edu.BaseTest;
import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.User;
import org.apache.log4j.LogManager;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class CounterAspectTestCase extends BaseTest {

    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(CounterAspectTestCase.class);

    @Test
    public void testAfterEventGetByName() {
        Map<Event, Long> testEventCounts = randomEventsMap();

        LOGGER.info("\n================== testAfterEventGetByName: ==================");
        LOGGER.info("\n testEventCounts: ");

        for (Event event : testEventCounts.keySet()) {
            LOGGER.info("Event getByName for " + event.toString());
            for (int i = 0; i < testEventCounts.get(event); i++) {
                LOGGER.info(eventRepository.getByName(event.getName()).equals(event) ? " + " : " - ");
            }
            LOGGER.debug("");
        }

        Map<Event, Long> counterAspectEventGetByName = counterAspect.getCounterEventGetByName();
        assertEquals(counterAspectEventGetByName, testEventCounts);
    }

    @Test
    public void testAfterEventGetTicketPrice() {
        Map<Event, Long> testEventCounts = randomEventsMap();
        User user = randomUser();

        LOGGER.info("\n================== testAfterEventGetTicketPrice: ==================");
        LOGGER.info("\n testEventCounts: ");

        for (Event event : testEventCounts.keySet()) {
            LOGGER.debug("Event " + event.getName());
            for (int i = 0; i < testEventCounts.get(event); i++) {
                LOGGER.info(" " + ticketService.getTicketPrice(event, event.getAirDate(), user) + " ");
            }
            LOGGER.debug("\n");
        }

        Map<Event, Long> counterAspectEventGetTicketPrice = counterAspect.getCounterEventGetTicketPrice();
        assertEquals(counterAspectEventGetTicketPrice, testEventCounts);
    }

    @Test
    public void testAfterEventTicketBooked() {
        Map<Event, Long> testEventCounts = randomEventsMap();
        User user = randomUser();

        LOGGER.info("\n==================  testAfterEventTicketBooked:================== ");
        LOGGER.info(" testEventCounts: ");
        for (Event event : testEventCounts.keySet()) {

            for (int i = 0; i < testEventCounts.get(event); i++) {
                LOGGER.info("Ticket for event '" + event.getName()
                    + "' booked = " + ticketService.bookTicket(event,
                    randomTestGenerator.getRandomDate(),
                    testGenerator.getRandomNumeric(),
                    user));
            }
        }
        Map<Event, Long> counterAspectEventTicketBooked = counterAspect.getCounterEventTicketBooked();
        assertEquals(counterAspectEventTicketBooked, testEventCounts);
    }

    private User randomUser() {
        User user = randomTestGenerator.randomUser();
        userRepository.put(user);
        return user;
    }

    private Map<Event, Long> randomEventsMap() {
        List<Event> events = randomTestGenerator.randomEventsList(5 + randomTestGenerator.nextInt(5));
        Map<Event, Long> testEventCounts = new HashMap<>();
        for (Event e : events) {
            e.setAuditorium(auditoriumService.getRandomAuditorium());
            eventRepository.put(e);
            testEventCounts.put(e, testEventCounts.getOrDefault(e, 0L) + 1L);
        }
        return testEventCounts;
    }
}
