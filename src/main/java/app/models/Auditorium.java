package app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "auditoriums")
@Builder
@AllArgsConstructor
public class Auditorium {

    public Auditorium() {
    }

    @Id
    private String name;

    @Column(name = "amount_of_seats")
    private long amountOfSeats;

    @OneToMany(mappedBy = "auditorium", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<VipSeat> vipSeats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmountOfSeats() {
        return amountOfSeats;
    }

    public void setAmountOfSeats(long amountOfSeats) {
        this.amountOfSeats = amountOfSeats;
    }

    public List<VipSeat> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(List<VipSeat> vipSeats) {
        this.vipSeats = vipSeats;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "name='" + name + '\'' +
                ", amountOfSeats=" + amountOfSeats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditorium)) return false;
        Auditorium that = (Auditorium) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }
}
