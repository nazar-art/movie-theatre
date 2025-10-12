package net.lelyak.edu.discounts;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.User;

import java.util.Date;

public interface DiscountStrategy {
    double getDiscount(User user, Event event, Date date);
}
