package net.lelyak.edu.aspects;

import net.lelyak.edu.dao.impl.AspectCounterDAO;
import net.lelyak.edu.discounts.IDiscountStrategy;
import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.utils.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * DiscountAspect - count how many times each discount was given total and for specific user
 */
@Aspect
public class DiscountAspect {

    @Autowired
    private AspectCounterDAO aspectCounterDAO;

    private final Map<IDiscountStrategy, Record> discountCounter = new HashMap<>();

    public static class Record {
        long totalCount;
        Map<User, Long> countPerUser = new HashMap<>();

        public long getTotalCount() {
            return totalCount;
        }

        public Map<User, Long> getCountPerUser() {
            return countPerUser;
        }
    }


    @Pointcut(value = "execution(* getDiscount(..)) && args(user, event, date)", argNames = "user,event,date")
    private void execDiscountStrategyGetDiscount(User user, Event event, Date date) {
    }

    @Pointcut("target(net.lelyak.edu.discounts.IDiscountStrategy)")
    private void targetDiscountStrategy() {
    }

    @AfterReturning(
        pointcut = "execDiscountStrategyGetDiscount(user, event, date) && targetDiscountStrategy()",
        returning = "discount", argNames = "jp,user,event,date,discount")
    public void afterDiscountStrategyGetDiscount(JoinPoint jp, User user, Event event, Date date, double discount) {

        Logger.info("afterDiscountStrategyGetDiscount for " + jp.getTarget().getClass().getName());

        if (discount != 0 && IDiscountStrategy.class.isInstance(jp.getTarget())) {

            IDiscountStrategy discountStrategy = (IDiscountStrategy) jp.getTarget();

            if (!discountCounter.containsKey(discountStrategy)) {
                discountCounter.put(discountStrategy, new Record());
            }


            Record record = discountCounter.get(discountStrategy);
            record.totalCount++;

            if (record.countPerUser.containsKey(user)) {
                record.countPerUser.put(user, record.countPerUser.get(user) + 1);
            } else {
                record.countPerUser.put(user, 1L);
            }
        }
    }

    public Map<IDiscountStrategy, Record> getDiscountCounter() {
        return discountCounter;
    }
}
