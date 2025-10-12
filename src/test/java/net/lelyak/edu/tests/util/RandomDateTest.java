package net.lelyak.edu.tests.util;


import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import static org.testng.Assert.assertTrue;


public class RandomDateTest {

    @Test
    public void testRandomDate() throws ParseException {
        Random random = new Random();

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        DateFormat odf = new SimpleDateFormat("yyyy.MM.dd hh:mm");

        RandomDate randomDate = new RandomDate(dateFormat.parse("1960.3.23"), dateFormat.parse("2054.12.31"));
        for (int i = 0; i < random.nextInt(100); i++) {
            System.out.println(odf.format(randomDate.get()));
        }
        assertTrue(true);
    }

}
