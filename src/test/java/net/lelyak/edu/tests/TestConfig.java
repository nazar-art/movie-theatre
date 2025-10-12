package net.lelyak.edu.tests;

import net.lelyak.edu.tests.util.RandomDate;
import net.lelyak.edu.utils.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
@ComponentScan("net.lelyak.edu.tests.util")
public class TestConfig {

    @Bean
    RandomDate randomDate() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        RandomDate rd;
        try {
            rd = new RandomDate(df.parse("01.01.1980"), df.parse("31.12.2024"));
        } catch (ParseException e) {
            Logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return rd;
    }
}
