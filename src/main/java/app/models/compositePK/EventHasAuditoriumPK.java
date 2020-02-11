package app.models.compositePK;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class EventHasAuditoriumPK implements Serializable {
    private Timestamp airDate;
    private String auditorium;

    public Timestamp getAirDate() {
        return airDate;
    }

    public void setAirDate(Timestamp airDate) {
        this.airDate = airDate;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventHasAuditoriumPK)) return false;
        EventHasAuditoriumPK that = (EventHasAuditoriumPK) o;
        return Objects.equals(getAirDate(), that.getAirDate()) &&
                Objects.equals(getAuditorium(), that.getAuditorium());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAirDate(), getAuditorium());
    }
}
