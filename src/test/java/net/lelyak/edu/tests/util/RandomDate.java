package net.lelyak.edu.tests.util;

import java.util.Date;
import java.util.Random;

public class RandomDate {
    private final Date minDate, maxDate;
    private final Random random = new Random(47);

    public RandomDate(Date minDate, Date maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    public Date get() {
        long min = minDate.getTime();
        long max = maxDate.getTime();
        long millis = min + ((long) (random.nextDouble() * (max - min)));
        return new Date(millis);
    }
}
