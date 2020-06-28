package app.services;

import app.models.EventHasAuditorium;
import app.models.User;
import app.services.discounts.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    @Autowired
    private List<DiscountStrategy> discountStrategies;

    /**
     * @param user
     * @param eventHasAuditorium
     * @param numberOfTickets
     * @param totalPrice
     * @return amount of money to be discount
     */
    public Double getDiscount(User user, EventHasAuditorium eventHasAuditorium, int numberOfTickets, double totalPrice) {

        return discountStrategies.stream()
                .map(d -> d.calculateDiscount(user, eventHasAuditorium, numberOfTickets, totalPrice))
                .max(Double::compareTo)
                .get();
    }
}
