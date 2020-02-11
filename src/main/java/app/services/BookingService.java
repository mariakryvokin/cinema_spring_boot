package app.services;

import app.models.Auditorium;
import app.models.Event;
import app.models.EventHasAuditorium;
import app.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class BookingService {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private EventHasAuditoriumService eventHasAuditoriumService;
    @Autowired
    private AuditoriumService auditoriumService;

    @Transactional
    public Map<EventHasAuditorium, List<Long>> getFreeSeats(){
        Map<EventHasAuditorium, List<Long>> freeSeats = new HashMap<>();
        Map<EventHasAuditorium, List<Long>> occupiedSeats = ticketService.getAll().stream().collect(Collectors.groupingBy(Ticket::getEventHasAuditorium, Collectors.mapping(Ticket::getSeat, Collectors.toList())));

        List<EventHasAuditorium> eventHasAuditoriums = eventHasAuditoriumService.getAll();

        List<String> auditoriumsNames = eventHasAuditoriums.stream().map(e->e.getAuditorium().getName()).collect(Collectors.toList());
        List<Auditorium> auditoriums = auditoriumService.getAllByNameIn(auditoriumsNames);

        Map<Auditorium, List<Long>> allSeatsInAuditorium = auditoriums.stream().collect(Collectors.toMap(Function.identity(), a -> LongStream.rangeClosed(1, a.getAmountOfSeats())
                .boxed().collect(Collectors.toList())));

        eventHasAuditoriums.stream().forEach(e -> {
            List<Long> seats = allSeatsInAuditorium.get(e.getAuditorium());
            if (seats != null) {
                if (occupiedSeats.get(e) != null) {
                    seats.removeAll(occupiedSeats.get(e));
                }
                freeSeats.put(e, seats);
            }
        });
        return freeSeats;
    }

}
