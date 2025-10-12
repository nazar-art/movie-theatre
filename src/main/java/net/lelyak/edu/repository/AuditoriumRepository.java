package net.lelyak.edu.repository;


import net.lelyak.edu.dao.impl.AuditoriumDAO;
import net.lelyak.edu.entity.Auditorium;

public class AuditoriumRepository extends BaseRepository<Auditorium, AuditoriumDAO> {

    @Override
    public Auditorium preSave(Auditorium entity) {
        return entity;
    }

    @Override
    public Auditorium postLoad(Auditorium entity) {
        return entity;
    }

}
