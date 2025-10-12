package net.lelyak.edu.tests.services;

import net.lelyak.edu.BaseTest;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

public class UserServiceTestCase extends BaseTest {

    private User testUser;

    @Test
    public void testGetUserByMail() throws Exception {
        testUser = testGenerator.getRandomUser();

        userService.register(testUser);

        User byEmail = userService.getByEmail(testUser.getEmail());

        String actualName = byEmail.getName();
        assertEquals(actualName, testUser.getName(), "first name is not as expected");
    }

    @Test
    public void testUserById() throws Exception {
        testUser = testGenerator.getRandomUser();
        userService.register(testUser);

        User userById = userService.getById(testUser.getId());

        String actualFirstName = userById.getName();
        assertEquals(actualFirstName, testUser.getName());
    }

    @Test
    public void testGetUserByName() throws Exception {
        testUser = testGenerator.getRandomUser();
        userService.register(testUser);

        User ron = userService.getByName(testUser.getName());

        String lastName = ron.getName();
        assertEquals(lastName, testUser.getName());
    }

    @Test
    public void testRegisterAndRemoveNewUser() throws Exception {
        testUser = testGenerator.getRandomUser();
        userService.register(testUser);

        User createdUser = userService.getById(testUser.getId());

        assertEquals(createdUser.getName(), testUser.getName());
        assertEquals(createdUser.getEmail(), testUser.getEmail());
        assertEquals(createdUser.getRole(), testUser.getRole());

        userService.remove(testUser);
        assertFalse(userRepository.isUserExist(testUser.getEmail()));
    }

    @Test
    public void testUserCount() throws Exception {
        testUser = testGenerator.getRandomUser();

        int countBeforeInsert = userService.getTotalUsersCount();
        userService.register(testUser, testUser.getBirthday());
        int countAfterInsert = userService.getTotalUsersCount();
        assertEquals(countAfterInsert, countBeforeInsert + 1, "total user count is not incremented after saving new user");

        userService.remove(testUser);
        int countAfterRemove = userService.getTotalUsersCount();
        assertEquals(countAfterRemove, countBeforeInsert, "total user is not decrementing after removing some user");
    }

    @Test
    public void testGetUserBookedTickets() throws Exception {
        int userIndex = 3;
        User testUser = userService.getById(userIndex);

//        Set<Ticket> bookedTickets = userService.getBookedTickets(testUser);
        List<Ticket> ticketsForUser = ticketService.getTicketsForUser(testUser);
        assertNotNull(ticketsForUser, "booked tickets for user can not be null");
    }
}
