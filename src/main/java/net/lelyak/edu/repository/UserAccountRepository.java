package net.lelyak.edu.repository;

import net.lelyak.edu.dao.impl.UserAccountDAO;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.UserAccount;

public class UserAccountRepository extends BaseRepository<UserAccount, UserAccountDAO> {

    @Override
    public UserAccount preSave(UserAccount entity) {
        return entity;
    }

    @Override
    public UserAccount postLoad(UserAccount entity) {
        return entity;
    }


    public UserAccount getForUser(User user) {
        return getDao().getForUser(user);
    }
}
