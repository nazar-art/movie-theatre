package net.lelyak.edu.dao.impl;

import net.lelyak.edu.dao.BaseDAO;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.UserAccount;
import net.lelyak.edu.utils.Logger;
import net.lelyak.edu.utils.StringUtilities;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Arrays;

public class UserAccountDAO extends BaseDAO<UserAccount> {

    private static final String USER_ACCOUNT_TABLE_NAME = "t_useraccount";
    private static final String[] accountFields = {"balance", "user_id"};

    public UserAccountDAO() {
        super(UserAccount.class, USER_ACCOUNT_TABLE_NAME, Arrays.asList(accountFields));
    }

    public UserAccount getForUser(User user) {
        String sql = StringUtilities
            .appendStrings("SELECT * FROM %s WHERE user_id=:user_id", USER_ACCOUNT_TABLE_NAME);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource("user_id", user.getId());

        Logger.info(StringUtilities.appendStrings("getForUser( %s ), SQL : [%s] ", user, sql));

        UserAccount userAccount = getNamedParameterJdbcTemplate()
            .queryForObject(sql, parameterSource, new BeanPropertyRowMapper<>(UserAccount.class));

        Logger.info(StringUtilities.appendStrings("getForUser( %s ) : %s ", user, user.toString()));

        return userAccount;
    }
}
