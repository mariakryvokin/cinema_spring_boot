package app.services;

import app.models.Auditorium;
import app.repositories.AuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriumService {

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    public List<Auditorium> getAll(){
        return auditoriumRepository.findAll();
    }

    public List<Auditorium> getAllByNameIn(List<String> names){
        return auditoriumRepository.getAllByNameIn(names);
    }

    public Auditorium getByName(String name){
        return auditoriumRepository.getOne(name);
    }
}
