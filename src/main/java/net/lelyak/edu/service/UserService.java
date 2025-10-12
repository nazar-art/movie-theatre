package net.lelyak.edu.service;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.enums.UserRole;
import net.lelyak.edu.repository.EventRepository;
import net.lelyak.edu.repository.UserRepository;
import net.lelyak.edu.utils.Logger;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * UserService - Manages registered users
 * register, remove, getById, getUserByEmail, getUsersByName, getBookedTickets
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketService ticketService;


    public long register(User user) {
        return userRepository.put(user);
    }

    public void register(User user, Date userBirthday) {
        Validate.notNull(user, "user can not be null for registration");
        Validate.notNull(userBirthday, "userBirthday can not be null");

        user.setRole(UserRole.REGISTERED_USER.toString());
        user.setBirthday(userBirthday);
        register(user);
    }

    public User signIn(String name) {
        return userRepository.getByName(name);
    }

    public User register(String name, String email, Date birthday) {
        User user = signIn(name);

        if (user == null) {
            user = new User();
            user.setBirthday(birthday);
            user.setEmail(email);
            user.setName(name);
            user.setRole(UserRole.REGISTERED_USER.toString());
            userRepository.put(user);
        }
        return signIn(name);
    }

    public void remove(User user) {
        userRepository.remove(user);
    }

    public User getById(long id) {
        return userRepository.getById(id);
    }

    public User getByEmail(String email) {
        Logger.info("Looking for the user with mail: " + email);
        return userRepository.getUserByEmail(email);
    }

    public User getByName(String name) {
        Logger.info("Looking for the user with name: " + name);
        return userRepository.getByName(name);
    }

    public int getTotalUsersCount() {
        return userRepository.getTotalCount();
    }

    public List<Ticket> getBookedTickets(User user) {
        return ticketService.getTicketsForUser(user);
    }

    public Collection<Event> viewEvents() {
        return eventRepository.getAll();
    }

    public Double getPrice(Event event, Date date, User user) {
        return ticketService.getTicketPrice(event, date, user);
    }

    public Ticket buyTicket(Event event, Date date, String sits, User user) {
        return ticketService.bookTicket(event, date, sits, user);
    }

    public Collection<Ticket> listTickets(User user) {
        return ticketService.getTicketsForUser(user);
    }

    public List<User> getAll() {
        return new ArrayList<>(userRepository.getAll());
    }

    public boolean isUserExist(User user) {
        return userRepository.isUserExist(user.getEmail());
    }
}
