package net.lelyak.edu.entity;

import net.lelyak.edu.entity.enums.Rating;
import net.lelyak.edu.utils.datafactory.InjectRandomData;
import net.lelyak.edu.utils.datafactory.RandomType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Event extends BaseEntity {

    @XmlAttribute
    @InjectRandomData(type = RandomType.FUTURE_DATE, min = 5, max = 10)
    private Date airDate;

    @XmlAttribute
    @InjectRandomData(type = RandomType.PRICE)
    private Double ticketPrice;

    @XmlAttribute
    @InjectRandomData(type = RandomType.EVENT_RATING)
    private Rating rating;

    @XmlElement
    private Auditorium auditorium;

    @XmlAttribute
    //Lazy loading
    private Long auditorium_id;

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Rating getEnumRating() {
        return rating;
    }

    public void setEnumRating(Rating rating) {
        this.rating = rating;
    }

    public void setRating(String rating) {
        this.rating = Rating.valueOf(rating);
    }

    public String getRating() {
        return this.rating.toString();
    }

    public Date getAirDate() {
        return airDate;
    }

    public void setAirDate(Date date) {
        this.airDate = date;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Long getAuditorium_id() {
        return auditorium_id;
    }

    public void setAuditorium_id(Long auditorium_id) {
        this.auditorium_id = auditorium_id;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", name=" + name +
            ", airDate=" + airDate +
            ", ticketPrice=" + ticketPrice +
            ", rating=" + rating +
            ", auditorium=" + auditorium +
            ", auditorium_id=" + auditorium_id +
            '}';
    }
}
