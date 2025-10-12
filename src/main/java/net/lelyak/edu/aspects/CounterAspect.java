package net.lelyak.edu.aspects;

import net.lelyak.edu.dao.impl.AspectCounterDAO;
import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.utils.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * CounterAspect - count how many times each event was accessed by name,
 * how many times its prices were queried,
 * how many times its tickets were booked.
 * <p/>
 * Store counters in map for now (later could be replaced by DB dao)
 */
@Aspect
public class CounterAspect {

    private final HashMap<Event, Long> counterEventGetByName = new HashMap<>();
    private final HashMap<Event, Long> counterEventGetTicketPrice = new HashMap<>();
    private final HashMap<Event, Long> counterEventTicketBooked = new HashMap<>();

    @Autowired
    private AspectCounterDAO aspectCounterDAO;


    @Pointcut("execution(* getByName(..))")
    private void execEventRepositoryGetByName() {
    }

    @Pointcut("target(net.lelyak.edu.repository.EventRepository)")
    private void withinEventRepository() {
    }

    @Pointcut("execution(* getTicketPrice(..)) && args(event, ..)")
    private void execTicketServiceGetTicketPrice(Event event) {
    }

    @Pointcut("target(net.lelyak.edu.service.TicketService)")
    private void withinTicketService() {
    }

    @Pointcut("execution(* *..TicketService.bookTicket(..))")
    private void execEventTicketBooked() {
    }

    @AfterReturning(
        pointcut = "execEventRepositoryGetByName() && withinEventRepository()",
        returning = "event")
    public void afterExecEventRepositoryGetByName(JoinPoint jp, Event event) {

        if (counterEventGetByName.containsKey(event)) {
            counterEventGetByName.put(event, counterEventGetByName.get(event) + 1);
        } else {
            counterEventGetByName.put(event, 1L);
        }

        Logger.info("afterEventGetByName from " + jp.getTarget().toString() + ", event: " + event);
    }

    @AfterReturning(
        pointcut = "execTicketServiceGetTicketPrice(event)", argNames = "jp,event")
    public void afterTicketServiceGetTicketPrice(JoinPoint jp, Event event) {

        if (counterEventGetTicketPrice.containsKey(event)) {
            counterEventGetTicketPrice.put(event, counterEventGetTicketPrice.get(event) + 1);
        } else {
            counterEventGetTicketPrice.put(event, 1L);
        }

        Logger.info("afterEventGetTicketPrice  from " + jp.getTarget().toString() + ", event: " + event);
    }

    @AfterReturning(
        pointcut = "execEventTicketBooked()",
        returning = "ticket")
    public void afterEventTicketBooked(JoinPoint jp, Ticket ticket) {

        Event event = ticket.getEvent();

        if (counterEventTicketBooked.containsKey(event)) {
            counterEventTicketBooked.put(event, counterEventTicketBooked.get(event) + 1);
        } else {
            counterEventTicketBooked.put(event, 1L);
        }

        Logger.info("afterEventTicketBooked  from " + jp.getTarget().toString() + ", event: " + event);
    }

    public HashMap<Event, Long> getCounterEventGetByName() {
        return counterEventGetByName;
    }

    public HashMap<Event, Long> getCounterEventGetTicketPrice() {
        return counterEventGetTicketPrice;
    }

    public HashMap<Event, Long> getCounterEventTicketBooked() {
        return counterEventTicketBooked;
    }

}
