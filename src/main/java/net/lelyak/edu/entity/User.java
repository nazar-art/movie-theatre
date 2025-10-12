package net.lelyak.edu.entity;

import net.lelyak.edu.utils.datafactory.InjectRandomData;
import net.lelyak.edu.utils.datafactory.RandomType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends BaseEntity {

    @XmlAttribute
    @InjectRandomData(type = RandomType.BIRTH_DATE)
    private Date birthday;

    @XmlAttribute
    @InjectRandomData(type = RandomType.EMAIL)
    private String email;

    @XmlAttribute
    private String password;

    @XmlAttribute
    @InjectRandomData(type = RandomType.ROLE)
    private String role;

    //    @InjectRandomData(type = RandomType.ROLE)
//    private List<UserRole> roles;
    @XmlAttribute
    private boolean enabled;

    public User() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addTicket(Ticket ticket) {
        ticket.setUser(this);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }*/

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name=" + name +
            ", email='" + email + '\'' +
            ", birthday=" + birthday +
            ", roles=" + role +
            ", enabled=" + enabled +
            '}';
    }
}
