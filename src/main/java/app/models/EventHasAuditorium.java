package app.models;

import app.models.compositePK.EventHasAuditoriumPK;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "event_has_auditoriums")
@IdClass(EventHasAuditoriumPK.class)
public class EventHasAuditorium {

    @Id
    @Column(name = "air_date")
    private Timestamp airDate;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auditoriums_name")
    private Auditorium auditorium;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "events_id")
    private Event event;

    @OneToMany(mappedBy = "eventHasAuditorium")
    private List<Ticket> tickets;

    public Timestamp getAirDate() {
        return airDate;
    }

    public void setAirDate(Timestamp airDate) {
        this.airDate = airDate;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventHasAuditorium)) return false;
        EventHasAuditorium that = (EventHasAuditorium) o;
        return Objects.equals(getAirDate(), that.getAirDate()) &&
                Objects.equals(getAuditorium(), that.getAuditorium());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAirDate(), getAuditorium());
    }
}
