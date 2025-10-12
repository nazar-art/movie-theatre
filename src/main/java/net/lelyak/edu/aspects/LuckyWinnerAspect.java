package net.lelyak.edu.aspects;

import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.utils.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * LuckyWinnerAspect - every time the bookTicket method is executed perform the checkLucky method for the user that based on some randomness will return true or false.
 * If user is lucky, the ticketPrice changes to zero and ticket is booked, thus user pays nothing.
 * <p/>
 * Store the information about this lucky event into the user object (like some system messages or so) - OPTIONAL
 */
@Aspect
public class LuckyWinnerAspect {

    @Pointcut("execution(* * ..BookingService.bookTicket(..))")
    public void accessEventTicketBooking() {
    }

    @Around(value = "accessEventTicketBooking() && args(user, ticket)",
            argNames = "joinPoint,user,ticket")
    public void checkLuckyUser(ProceedingJoinPoint joinPoint, User user, Ticket ticket) {
        Object targetClass = joinPoint.getTarget();
        Class<?> bookingServiceClass = targetClass.getClass();

        try {
            Method method = bookingServiceClass.getMethod("checkLucky", User.class);
            method.setAccessible(true);
            boolean isUserLucky = (boolean) method.invoke(targetClass, user);

            if (isUserLucky) {
                // set ticket price to 0
                ticket.setPrice(0D);
                Logger.info("Ticket price is set to: " + ticket.getPrice());
            }

            joinPoint.proceed(new Object[]{user, ticket});
        } catch (Throwable throwable) {
            Logger.error("Exception during checkLucky user: " + throwable.getMessage());
        }
    }

}
