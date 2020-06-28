package app.services;

import app.models.Auditorium;
import app.models.EventHasAuditorium;
import app.models.Ticket;
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

    private TicketService ticketService;
    private EventHasAuditoriumService eventHasAuditoriumService;
    private AuditoriumService auditoriumService;

    public BookingService(TicketService ticketService, EventHasAuditoriumService eventHasAuditoriumService,
                          AuditoriumService auditoriumService) {
        this.ticketService = ticketService;
        this.eventHasAuditoriumService = eventHasAuditoriumService;
        this.auditoriumService = auditoriumService;
    }

    @Transactional
    public Map<EventHasAuditorium, List<Long>> getFreeSeats() {
        Map<EventHasAuditorium, List<Long>> occupiedSeats = getOccupiedSeats();
        List<EventHasAuditorium> eventHasAuditoriums = eventHasAuditoriumService.getAll();
        List<String> auditoriumsNames = getAuditoriumsNames(eventHasAuditoriums);
        List<Auditorium> auditoriumsWithEvents = auditoriumService.getAllByNameIn(auditoriumsNames);
        Map<Auditorium, List<Long>> allSeatsInAuditorium = getAllSeatsInAuditorium(auditoriumsWithEvents);
        return findFreeSeats(occupiedSeats, eventHasAuditoriums, allSeatsInAuditorium);
    }

    private Map<EventHasAuditorium, List<Long>> findFreeSeats(Map<EventHasAuditorium,
            List<Long>> occupiedSeats, List<EventHasAuditorium> eventHasAuditoriums,
                                                              Map<Auditorium, List<Long>> allSeatsInAuditorium) {
        Map<EventHasAuditorium, List<Long>> freeSeats = new HashMap<>();
        eventHasAuditoriums.stream().forEach(eventHasAuditorium -> {
            List<Long> seats = allSeatsInAuditorium.get(eventHasAuditorium.getAuditorium());
            if (occupiedSeats.get(eventHasAuditorium) != null) {
                seats.removeAll(occupiedSeats.get(eventHasAuditorium));
            }
            freeSeats.put(eventHasAuditorium, seats);
        });
        return freeSeats;
    }

    private Map<Auditorium, List<Long>> getAllSeatsInAuditorium(List<Auditorium> auditoriums) {
        return auditoriums.stream()
                .collect(Collectors.toMap(Function.identity(), a -> LongStream.rangeClosed(1, a.getAmountOfSeats())
                        .boxed().collect(Collectors.toList())));
    }

    private List<String> getAuditoriumsNames(List<EventHasAuditorium> eventHasAuditoriums) {
        return eventHasAuditoriums.stream()
                .map(e -> e.getAuditorium().getName())
                .collect(Collectors.toList());
    }

    private Map<EventHasAuditorium, List<Long>> getOccupiedSeats() {
        return ticketService.getAll()
                .stream()
                .collect(Collectors
                        .groupingBy(Ticket::getEventHasAuditorium,
                                Collectors.mapping(Ticket::getSeat, Collectors.toList())));
    }

}
