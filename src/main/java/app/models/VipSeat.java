package app.models;

import app.models.compositePK.VipSeatPK;

import javax.persistence.*;

@Entity
@Table(name = "vip_seats")
@IdClass(VipSeatPK.class)
public class VipSeat {

    @Id
    private long number;

    @Id
    @ManyToOne
    @JoinColumn(name = "auditoriums_name")
    private Auditorium auditorium;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }
}
