package net.lelyak.edu.entity;

public class UserAccount extends BaseEntity {
    private Double balance;
    private Long user_id;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + id +
            ", balance=" + balance +
            ", user_id=" + user_id +
            '}';
    }
}
