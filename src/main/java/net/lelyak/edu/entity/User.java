package net.lelyak.edu.entity;

import net.lelyak.edu.utils.Logger;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private Integer id;
    private String name;
    private Calendar birthday;
    private String email;
    private Role role;
    private Set<Ticket> bookedTickets = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id, String name, String email, Set<Ticket> bookedTickets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bookedTickets = bookedTickets;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public void addTicket(Ticket ticket) {
        if (ticket != null && !bookedTickets.contains(ticket)) {
            bookedTickets.add(ticket);
            Logger.info("Add one ticket: " + ticket + " to user: " + this.toString());
            ticket.setUser(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(email, user.email) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        if (this.id != null) {
            return this.id;
        }
        return Objects.hash(name, birthday, email, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", role=" + role +
                ", bookedTickets=" + bookedTickets +
                '}';
    }
}
