package app.models.compositePK;

import java.io.Serializable;
import java.util.Objects;

public class VipSeatPK implements Serializable {
    private long number;
    private String auditorium;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
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
        if (!(o instanceof VipSeatPK)) return false;
        VipSeatPK vipSeatPK = (VipSeatPK) o;
        return getNumber() == vipSeatPK.getNumber() &&
                Objects.equals(getAuditorium(), vipSeatPK.getAuditorium());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNumber(), getAuditorium());
    }
}
