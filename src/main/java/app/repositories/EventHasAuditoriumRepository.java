package app.repositories;

import app.models.EventHasAuditorium;
import app.models.compositePK.EventHasAuditoriumPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface EventHasAuditoriumRepository extends JpaRepository<EventHasAuditorium, EventHasAuditoriumPK> {

    List<EventHasAuditorium> getAllByAirDateBetween(Timestamp from, Timestamp to);
}
