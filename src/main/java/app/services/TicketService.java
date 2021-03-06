package app.services;

import app.models.Auditorium;
import app.models.EventHasAuditorium;
import app.models.Ticket;
import app.models.User;
import app.models.VipSeat;
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

    private TicketRepository ticketRepository;
    private VipSeatRepository vipSeatRepository;
    private DiscountService discountService;

    public TicketService(TicketRepository ticketRepository, VipSeatRepository vipSeatRepository,
                         DiscountService discountService) {
        this.ticketRepository = ticketRepository;
        this.vipSeatRepository = vipSeatRepository;
        this.discountService = discountService;
    }

    public Ticket save(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public double getTicketsPrice(EventHasAuditorium eventHasAuditorium, User user, List<Integer> seats) {
        double resultPrice = eventHasAuditorium.getEvent().getBasePrice();
        if (eventHasAuditorium.getEvent().getRating() == Rating.HIGH) {
            resultPrice *= 1.2;
        }
        List<Long> vipSeats = getVipSeatsForAuditorium(eventHasAuditorium.getAuditorium());
        long orderedVipSeatsAmount = seats.stream().filter(vipSeats::contains).count();
        double vipSeatCost = orderedVipSeatsAmount * resultPrice;
        resultPrice = vipSeatCost + (seats.size() - orderedVipSeatsAmount) * resultPrice;
        return resultPrice - discountService.getDiscount(user, eventHasAuditorium, seats.size(), resultPrice);
    }

    private List<Long> getVipSeatsForAuditorium(Auditorium auditorium) {
        return vipSeatRepository.findAllByAuditorium(auditorium).stream()
                .map(VipSeat::getNumber).collect(Collectors.toList());
    }

    public long countBookedTicketByUserId(long userId){
        return ticketRepository.countBookedTicketByUserId(userId);
    }

    public List<Ticket> getAllByAuditoriumNameAndAirDateTime(String auditoriumName, Timestamp airDateTime){
        return ticketRepository.getAllByEventHasAuditorium_Auditorium_NameAndEventHasAuditorium_AirDate(auditoriumName, airDateTime);
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
