package net.lelyak.edu.discounts;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.User;

import java.util.Calendar;
import java.util.Date;

public class BirthdayDiscount implements IDiscountStrategy {

    private static final double DISCOUNT = .05;

    @Override
    public double getDiscount(User user, Event event, Date date) {
        Calendar cl1 = Calendar.getInstance(),
            cl2 = Calendar.getInstance();

        cl1.setTime(user.getBirthday());
        cl2.setTime(date);

        if (cl1.get(Calendar.DAY_OF_MONTH) == cl2.get(Calendar.DAY_OF_MONTH)
            && cl1.get(Calendar.MONTH) == cl2.get(Calendar.MONTH))
            return DISCOUNT;
        else
            return 0;
    }

}
