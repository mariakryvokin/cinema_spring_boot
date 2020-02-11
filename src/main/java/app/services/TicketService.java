package app.services;

import app.models.EventHasAuditorium;
import app.models.Ticket;
import app.models.User;
import app.models.enums.Rating;
import app.repositories.TicketRepository;
import app.repositories.VipSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private VipSeatRepository vipSeatRepository;
    @Autowired
    private DiscountService discountService;

    public Ticket save(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public double getTicketsPrice(EventHasAuditorium eventHasAuditorium, User user, List<Integer> seats) {
        double resultPrice = eventHasAuditorium.getEvent().getBasePrice();
        if (eventHasAuditorium.getEvent().getRating() == Rating.HIGH) {
            resultPrice *= 1.2;
        }
        List<Long> vipSeats = vipSeatRepository.findAllByAuditorium(eventHasAuditorium.getAuditorium()).stream().map(v -> v.getNumber()).collect(Collectors.toList());
        long orderedVipSeats = seats.stream().filter(s -> vipSeats.contains(s)).count();
        double vipSeatCost = orderedVipSeats * resultPrice;
        resultPrice = vipSeatCost + (seats.size() - orderedVipSeats) * resultPrice;
        return resultPrice - discountService.getDiscount(user, eventHasAuditorium, seats.size(), resultPrice);
    }

    public long countBookedTicketByUserId(long userId){
        return ticketRepository.countBookedTicketByUserId(userId);
    }

    public List<Integer> getAllByAuditoriumNameAndAirDateTime(String auditoriumName, Timestamp airDateTime){
        return ticketRepository.getAllByAuditoriumNameAndAirDateTime(auditoriumName, airDateTime);
    }

    public List<Ticket> getAll(){
        return ticketRepository.findAll();
    }

    public List<Ticket> getCartTicketsByUserIdAndOrderId(Long userId, Long orderId){
        return ticketRepository.findAllByUser_IdAndOrder_Id(userId,orderId);
    }

    public List<Ticket> findAllByEventHasAuditorium_Event_Id(long eventId){
        return ticketRepository.findAllByEventHasAuditorium_Event_Id(eventId);
    }

    public Optional<Ticket> getTicketById(Long id){
        return ticketRepository.findById(id);
    }

    public List<Ticket> getUnpaidTickets(){
        return ticketRepository.findAllByOrder(null);
    }
}
