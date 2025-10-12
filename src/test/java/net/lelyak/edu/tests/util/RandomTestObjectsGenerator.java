package net.lelyak.edu.tests.util;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.enums.Rating;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class RandomTestObjectsGenerator {
    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(RandomTestObjectsGenerator.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy h:mm");

    @Autowired
    private DataFactory dataFactory;

    private final Random random = new Random();

    public Date getRandomDate() {
        Date date;
        try {
            date = DATE_FORMAT.parse("01.01.2025 15:00");
        } catch (ParseException e) {
            LOGGER.error("error during date parsing", e);
            throw new RuntimeException(e);
        }
        return dataFactory.getDate(date, 2, 260);
    }

    public String getRandomWord() {
        return dataFactory.getRandomWord();
    }

    public String getRandomSentence() {
        return dataFactory.getRandomText(2, 6);
    }

    public Event randomEvent() {
        Event event = new Event();
        event.setAirDate(getRandomDate());
        event.setName(RandomStringUtils.randomAlphabetic(10));
        event.setTicketPrice(random.nextDouble());
        event.setEnumRating(Rating.values()[random.nextInt(3)]);
        return event;
    }

    public List<Event> randomEventsList(int size) {
        Set<String> uniqueNames = new HashSet<>();
        List<Event> events = new ArrayList<>();
        while (events.size() < size) {
            Event event = randomEvent();
            if (uniqueNames.add(event.getName())) {
                events.add(event);
            }
        }
        return events;
    }

    public User randomUser() {
        User user = new User();
        user.setBirthday(dataFactory.getBirthDate());
        user.setName(dataFactory.getFirstName());
        user.setEmail(dataFactory.getEmailAddress());
        return user;
    }

    public List<User> randomUserList(int count) {
        List<User> testUsers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            testUsers.add(randomUser());
        }
        return testUsers;
    }

    public int nextInt(int bound) {
        return dataFactory.getNumberUpTo(bound);
    }
}
