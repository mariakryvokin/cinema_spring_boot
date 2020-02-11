package app.services.discounts;

import app.models.EventHasAuditorium;
import app.models.User;

public interface DiscountStrategy {

    double calculateDiscount(User user, EventHasAuditorium eventHasAuditorium, long amountOfTickets, double totalPrice);
}
