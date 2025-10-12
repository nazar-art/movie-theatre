package net.lelyak.edu.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Auditorium extends BaseEntity {

    @XmlAttribute
    private Integer seats;
    @XmlAttribute
    private String vip;

    public int getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                ", seats=" + seats +
                ", vip=" + vip +
                '}';
    }
}
