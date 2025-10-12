package net.lelyak.edu.tests.util;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.enums.Rating;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class RandomTestObjectsGenerator {

    @Autowired
    private RandomDate randomDate;

    private final Random random = new Random();

    public Date getRandomDate() {
        return randomDate.get();
    }

    public String getRandomWord() {
        return RandomStringUtils.randomAlphabetic(6);
    }

    public String getRandomSentence() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public Event randomEvent() {
        Event event = new Event();
        event.setAirDate(randomDate.get());
        event.setName(getRandomWord());
        event.setTicketPrice(random.nextDouble());
        event.setEnumRating(Rating.values()[random.nextInt(3)]);
        return event;
    }

    public List<Event> randomEventsList(int count) {
        List<Event> testEvents = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            testEvents.add(randomEvent());
        }
        return testEvents;
    }

    public User randomUser() {
        User user = new User();
        user.setBirthday(randomDate.get());
        user.setName(StringUtils.capitalize(getRandomWord()));
        user.setEmail(getRandomWord().concat("@test.com"));
        return user;
    }

    public List<User> randomUserList(int count) {
        List<User> testUsers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            testUsers.add(randomUser());
        }
        return testUsers;
    }

    public int nextInt() {
        return random.nextInt();
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public long nextLong() {
        return random.nextLong();
    }

}
