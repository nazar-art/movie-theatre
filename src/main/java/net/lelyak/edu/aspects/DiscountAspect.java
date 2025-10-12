package net.lelyak.edu.aspects;

import net.lelyak.edu.dao.impl.AspectCounterDAO;
import net.lelyak.edu.discounts.DiscountStrategy;
import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.User;
import org.apache.log4j.LogManager;
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

    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(DiscountAspect.class);

    @Autowired
    private AspectCounterDAO aspectCounterDAO;

    private final Map<DiscountStrategy, Record> discountCounter = new HashMap<>();

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

    @Pointcut("target(net.lelyak.edu.discounts.DiscountStrategy)")
    private void targetDiscountStrategy() {
    }

    @AfterReturning(
        pointcut = "execDiscountStrategyGetDiscount(user, event, date) && targetDiscountStrategy()",
        returning = "discount", argNames = "jp,user,event,date,discount")
    public void afterDiscountStrategyGetDiscount(JoinPoint jp, User user, Event event, Date date, double discount) {

        LOGGER.info("afterDiscountStrategyGetDiscount for " + jp.getTarget().getClass().getName());

        if (discount != 0 && DiscountStrategy.class.isInstance(jp.getTarget())) {

            DiscountStrategy discountStrategy = (DiscountStrategy) jp.getTarget();

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

    public Map<DiscountStrategy, Record> getDiscountCounter() {
        return discountCounter;
    }
}
