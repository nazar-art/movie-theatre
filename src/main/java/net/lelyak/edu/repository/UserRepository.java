package net.lelyak.edu.repository;


import net.lelyak.edu.dao.impl.UserDAO;
import net.lelyak.edu.entity.User;

/**
 * UserRepository - Manages registered users
 * <p>
 * register, remove, getById, getUserByEmail, getUsersByName, getBookedTickets
 */
public class UserRepository extends BaseRepository<User, UserDAO> {

    public User getUserByEmail(String email) {
        return getDao().getByEmail(email);
    }

    @Override
    public User preSave(User entity) {
        return entity;
    }

    @Override
    public User postLoad(User entity) {
        return entity;
    }

    public boolean isUserExist(String email) {
        return getDao().isUserPresented(email);
    }
}
