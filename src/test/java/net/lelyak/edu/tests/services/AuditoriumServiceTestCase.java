package net.lelyak.edu.tests.services;

import net.lelyak.edu.BaseTest;
import net.lelyak.edu.entity.Auditorium;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class AuditoriumServiceTestCase extends BaseTest {

    @Test
    public void testGetAuditoriums() {
        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        assertNotNull(auditoriums);

        Auditorium auditorium = auditoriums.get(1);
        String vipSeats = auditoriumService.getVipSeats(auditorium);
        assertNotNull(vipSeats);
    }

    @Test
    public void testAuditoriumSeatsNumbers() {
        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        Auditorium auditorium = auditoriums.get(0);

        int seatsNumber = auditoriumService.getSeatsNumber(auditorium);
        assertTrue(seatsNumber > 0);
    }

    @Test
    public void testAuditoriumVipSeats() {
        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        Auditorium auditorium = auditoriums.get(0);

        String vipSeats = auditoriumService.getVipSeats(auditorium);
        assertNotNull(vipSeats);
    }
}
