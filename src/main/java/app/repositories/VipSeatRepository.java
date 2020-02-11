package app.repositories;

import app.models.Auditorium;
import app.models.VipSeat;
import app.models.compositePK.VipSeatPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VipSeatRepository extends JpaRepository<VipSeat, VipSeatPK> {

    List<VipSeat> findAllByAuditorium(Auditorium auditorium);
}
