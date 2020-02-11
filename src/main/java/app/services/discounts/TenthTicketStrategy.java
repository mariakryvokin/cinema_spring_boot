package app.services.discounts;

import app.models.EventHasAuditorium;
import app.models.User;
import app.services.TicketService;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenthTicketStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(User user, EventHasAuditorium eventHasAuditorium, long amountOfTickets, double totalPrice) {
        double tenth = amountOfTickets/10 * (totalPrice / amountOfTickets);
        return tenth * 0.5;
    }
}
