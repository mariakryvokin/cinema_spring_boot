package app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tickets")
@Builder
@AllArgsConstructor
public class Ticket {
    public Ticket() {
    }

    @Id
    private long id;

    private long seat;

    @Column(name = "date_time")
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orders_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "event_has_auditoriums_auditoriums_name", referencedColumnName = "auditoriums_name"),
            @JoinColumn(name = "event_has_auditoriums_air_date", referencedColumnName = "air_date")
    })
    private EventHasAuditorium eventHasAuditorium;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeat() {
        return seat;
    }

    public void setSeat(long seat) {
        this.seat = seat;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public EventHasAuditorium getEventHasAuditorium() {
        return eventHasAuditorium;
    }

    public void setEventHasAuditorium(EventHasAuditorium eventHasAuditorium) {
        this.eventHasAuditorium = eventHasAuditorium;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
