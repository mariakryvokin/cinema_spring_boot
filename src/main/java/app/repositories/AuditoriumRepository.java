package app.repositories;

import app.models.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditoriumRepository extends JpaRepository<Auditorium,String> {

    List<Auditorium> getAllByNameIn(List<String> names);

}
