package app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "orders")
@Table(name = "orders")
@Builder
@AllArgsConstructor
public class Order {

    public Order() {
    }

    @Id
    private long id;

    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Ticket> tickets;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
