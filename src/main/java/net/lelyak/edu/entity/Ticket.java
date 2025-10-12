package net.lelyak.edu.entity;

import net.lelyak.edu.utils.datafactory.InjectRandomData;
import net.lelyak.edu.utils.datafactory.RandomType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket extends BaseEntity {

    @XmlAttribute
    @InjectRandomData(type = RandomType.PRICE, min = 5, max = 10)
    private Double price;

    @XmlAttribute
    @InjectRandomData(type = RandomType.FUTURE_DATE)
    private Date onDate;

    private Event event;
    private User user;

    //Lazy loading
    private Long user_id;
    private Long event_id;

    public Ticket() {
        super();
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("d MM yyyy H:mm");
        return "Ticket{" +
            "id=" + id +
            ", name=" + name +
            ", price=" + price +
            ", onDate=" + df.format(onDate) +
            ", event=" + event +
            ", user=" + user +
            '}';
    }
}
