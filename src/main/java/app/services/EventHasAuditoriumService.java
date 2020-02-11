package app.services;

import app.models.EventHasAuditorium;
import app.repositories.EventHasAuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventHasAuditoriumService {

    @Autowired
    private EventHasAuditoriumRepository eventHasAuditoriumRepository;

    public EventHasAuditorium save(EventHasAuditorium eventHasAuditorium){
        return eventHasAuditoriumRepository.save(eventHasAuditorium);
    }

    public List<EventHasAuditorium> getForDateRange(Timestamp from, Timestamp to){
        return eventHasAuditoriumRepository.getAllByAirDateBetween(from, to);
    }

    public List<EventHasAuditorium> getNextEvents(Timestamp to){
        return eventHasAuditoriumRepository.getAllByAirDateBetween(Timestamp.valueOf(LocalDateTime.now()), to);
    }

    public List<EventHasAuditorium> getAll(){
        return eventHasAuditoriumRepository.findAll();
    }
}
