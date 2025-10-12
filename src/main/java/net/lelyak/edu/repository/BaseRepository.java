package net.lelyak.edu.repository;

import net.lelyak.edu.dao.BaseDAO;
import net.lelyak.edu.entity.BaseEntity;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public abstract class BaseRepository<E extends BaseEntity, DAO extends BaseDAO<E>> {

    private DAO dao;

    public DAO getDao() {
        return dao;
    }

    public void setDao(DAO dao) {
        this.dao = dao;
    }


    public long put(E entity) {
        return dao.save(preSave(entity));
    }

    public void remove(E entity) {
        dao.delete(entity);
    }

    public E getById(long id) {
        return postLoad(dao.getById(id));
    }

    public E getByName(String name) {
        return postLoad(dao.getByName(name));
    }

    public Collection<E> getAll() {
        return dao.getAll().stream()
            .map(this::postLoad)
            .collect(toList());
    }

    public void putAll(Collection<E> entities) {
        dao.saveAll(entities.stream()
            .map(this::postLoad)
            .collect(toList()));
    }

    public int getTotalCount() {
        return dao.getTotalCount();
    }

    public abstract E preSave(E entity);

    public abstract E postLoad(E entity);
}
