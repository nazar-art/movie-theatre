package net.lelyak.edu.service;

import net.lelyak.edu.entity.Auditorium;
import net.lelyak.edu.repository.AuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * AuditoriumService - Returns info about auditoriums and places
 * <p>
 * Since auditorium information is usually static, store it in some property file. The information that needs to be stored is:
 * name
 * number of seats
 * vip seats (comma-separated list of expensive seats)
 * Several auditoriums can be stored in separate property files, information from them could be injected into the AuditoriumService
 * <p>
 * getAuditoriums(), getSeatsNumber(), getVip()
 */
@Service
public class AuditoriumService {

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    private final Random random = new Random();

    public List<Auditorium> getAuditoriums() {
        return new ArrayList<>(this.auditoriumRepository.getAll());
    }

    public int getSeatsNumber(Auditorium auditorium) {
        return auditorium.getSeats();
    }

    public String getVipSeats(Auditorium auditorium) {
        return auditorium.getVip();
    }

    public Auditorium getRandomAuditorium() {
        return auditoriumRepository.getById(1 + random.nextInt(3));
    }
}
