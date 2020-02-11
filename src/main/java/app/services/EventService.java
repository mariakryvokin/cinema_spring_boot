package app.services;

import app.models.Event;
import app.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event save(Event event){
        return eventRepository.save(event);
    }

    public List<Event> saveAll(Iterable<Event> event){
        return eventRepository.saveAll(event);
    }

    public void remove(Long eventId){
        eventRepository.deleteById(eventId);
    }

    public Event getById(Long eventId){
        return eventRepository.getOne(eventId);
    }

    public Event getByName(String name){
        return eventRepository.getByName(name);
    }

    public List<Event> getAll(){
        return eventRepository.findAll();
    }
}
