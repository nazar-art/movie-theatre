package net.lelyak.edu.configurations;

import net.lelyak.edu.discounts.BirthdayDiscount;
import net.lelyak.edu.discounts.HalfPriceTicketDiscount;
import net.lelyak.edu.discounts.DiscountStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DiscountConfiguration {

    @Bean
    public DiscountStrategy birthdayDiscount() {
        return new BirthdayDiscount();
    }

    @Bean
    public DiscountStrategy halfPriceTicketDiscount() {
        return new HalfPriceTicketDiscount();
    }

    @Bean
    public List<DiscountStrategy> discountStrategies() {
        return Arrays.asList(birthdayDiscount(), halfPriceTicketDiscount());
    }
}
