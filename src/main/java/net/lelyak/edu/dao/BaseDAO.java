package net.lelyak.edu.dao;

import net.lelyak.edu.entity.BaseEntity;
import net.lelyak.edu.utils.Logger;
import net.lelyak.edu.utils.StringUtilities;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

public abstract class BaseDAO<ENTITY extends BaseEntity> extends NamedParameterJdbcDaoSupport implements GenericDao<ENTITY> {

    private final String tableName;
    private final Class<ENTITY> entityClass;
    private final String insertSQL;
    private final String updateSQL;

    public BaseDAO(Class<ENTITY> entityClass, String tableName, List<String> fields) {
        this.entityClass = entityClass;
        this.tableName = tableName;

        // init SQLs
        StringBuilder sbInsertSQL = new StringBuilder();
        StringBuilder sbUpdateSQL = new StringBuilder();

        sbInsertSQL.append("INSERT INTO ").append(tableName).append(" (");
        sbUpdateSQL.append("UPDATE ").append(tableName).append(" SET ");

        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                sbInsertSQL.append(", ");
                sbUpdateSQL.append(", ");
            }
            sbInsertSQL.append(fields.get(i));
            sbUpdateSQL.append(fields.get(i)).append("=:").append(fields.get(i));
        }
        sbInsertSQL.append(") ").append("VALUES (");
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                sbInsertSQL.append(",");
            }
            sbInsertSQL.append(":").append(fields.get(i));
        }

        sbInsertSQL.append(")\n");
        sbUpdateSQL.append(" WHERE id=:id\n");

        this.insertSQL = sbInsertSQL.toString();
        this.updateSQL = sbUpdateSQL.toString();
        Logger.info("BaseDAO(), insertSQL: [" + insertSQL + "]");
        Logger.info("BaseDAO(), updateSQL: [" + updateSQL + "]");
    }

    @Override
    public Long save(ENTITY entity) {
        long res;
        if (entity.getId() == null) {
            res = insert(entity);
        } else {
            update(entity);
            res = entity.getId();
        }
        return res;
    }

    @Override
    public ENTITY getById(long id) {

        return getNamedParameterJdbcTemplate()
                .queryForObject(
                        String.format("SELECT * FROM %s WHERE id=:id", tableName),
                        new MapSqlParameterSource("id", id),
                        new BeanPropertyRowMapper<>(entityClass)
                );
    }

    @Override
    public ENTITY getByName(String name) {
        if (!exists(name)) {
            return null;
        }
        return getNamedParameterJdbcTemplate()
                .queryForObject(
                        String.format("SELECT * FROM %s WHERE name=:name", tableName),
                        new MapSqlParameterSource("name", name),
                        new BeanPropertyRowMapper<>(entityClass)
                );
    }

    @Override
    public void update(ENTITY entity) {
        getNamedParameterJdbcTemplate().update(updateSQL, new BeanPropertySqlParameterSource(entity));
        Logger.info("Update entity: " + entity);
    }

    @Override
    public void delete(ENTITY entity) {

        int rowCount = getNamedParameterJdbcTemplate().update(
                String.format("DELETE FROM %s WHERE id=:id", tableName),
                new MapSqlParameterSource("id", entity.getId())
        );

        Logger.info(StringUtilities
                .appendStrings("Delete %d %s rows deleted: %d", entity.getId(), entity.getName(), rowCount));
    }

    @Override
    public List<ENTITY> getAll() {
        return getNamedParameterJdbcTemplate().query(
                String.format("SELECT * FROM %s", tableName),
                new BeanPropertyRowMapper<>(entityClass));
    }

    @Override
    public int getTotalCount() {
        return getJdbcTemplate().queryForObject(
                String.format("SELECT COUNT(*) FROM %s", tableName),
                int.class);
    }

    private long insert(ENTITY entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(insertSQL, new BeanPropertySqlParameterSource(entity), keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        Logger.info("Insert user: " + entity.toString());
        return entity.getId();
    }

    protected boolean exists(long id) {
        long rowCount = getNamedParameterJdbcTemplate().queryForObject(
                String.format("SELECT COUNT(*) FROM %s WHERE id=:id", tableName),
                new MapSqlParameterSource("id", id),
                long.class);
        return rowCount > 0;
    }

    protected boolean existsByEmail(String email) {
        String sql = StringUtilities.appendStrings("SELECT COUNT(*) FROM %s WHERE email=:email", tableName);
        Logger.info("QUERY is: " + sql);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("email", email);
        long rowCount = getNamedParameterJdbcTemplate().queryForObject(sql, parameterSource, long.class);
        return rowCount > 0;
    }

    private boolean exists(String name) {
        String sql = StringUtilities.appendStrings("SELECT COUNT(*) FROM %s WHERE name=:name", tableName);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("name", name);
        long rowCount = getNamedParameterJdbcTemplate().queryForObject(sql, parameterSource, long.class);
        return rowCount > 0;
    }

}
