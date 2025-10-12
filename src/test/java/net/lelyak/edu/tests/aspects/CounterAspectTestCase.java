package net.lelyak.edu.tests.aspects;

import net.lelyak.edu.BaseTest;
import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.utils.Logger;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class CounterAspectTestCase extends BaseTest {

    @Test
    public void testAfterEventGetByName() {
        Map<Event, Long> testEventCounts = randomEventsMap();
        eventRepository.putAll(testEventCounts.keySet());

        Logger.info("\n================== testAfterEventGetByName: ==================");
        Logger.info("\n testEventCounts: ");

        for (Event event : testEventCounts.keySet()) {
            Logger.info("Event getByName for " + event.toString());
            for (int i = 0; i < testEventCounts.get(event); i++) {
                Logger.debug(eventRepository.getByName(event.getName()).equals(event) ? " + " : " - ");
            }
            Logger.debug("");
        }

        HashMap<Event, Long> counterAspectEventGetByName = counterAspect.getCounterEventGetByName();
        assertEquals(counterAspectEventGetByName, testEventCounts);
    }

    @Test
    public void testAfterEventGetTicketPrice() {
        Map<Event, Long> testEventCounts = randomEventsMap();

        User user = randomUser();

        Logger.info("\n================== testAfterEventGetTicketPrice: ==================");
        Logger.info("\n testEventCounts: ");

        for (Event event : testEventCounts.keySet()) {
            Logger.debug("Event " + event.getName());
            for (int i = 0; i < testEventCounts.get(event); i++) {
                Logger.info(" " + ticketService.getTicketPrice(event, event.getAirDate(), user) + " ");
            }
            Logger.debug("\n");
        }

        Map<Event, Long> counterAspectEventGetTicketPrice = counterAspect.getCounterEventGetTicketPrice();
        assertEquals(counterAspectEventGetTicketPrice, testEventCounts);
    }

    @Test
    public void testAfterEventTicketBooked() {
        Map<Event, Long> testEventCounts = randomEventsMap();
        User user = randomUser();

        Logger.info("\n==================  testAfterEventTicketBooked:================== ");
        Logger.info(" testEventCounts: ");
        for (Event event : testEventCounts.keySet()) {

            for (int i = 0; i < testEventCounts.get(event); i++) {
                Logger.info("Ticket for event '" + event.getName()
                    + "' booked = " + ticketService.bookTicket(event,
                    randomTestGenerator.getRandomDate(),
                    RandomStringUtils.randomNumeric(4),
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
        Map<Event, Long> testEventCounts = new HashMap<>();
        for (Event e : randomTestGenerator.randomEventsList(5 + randomTestGenerator.nextInt(5))) {
            e.setAuditorium(auditoriumService.getRandomAuditorium());
            eventRepository.put(e);
            testEventCounts.put(e, (long) (5 + randomTestGenerator.nextInt(5)));
        }
        eventRepository.putAll(testEventCounts.keySet());
        return testEventCounts;
    }
}
