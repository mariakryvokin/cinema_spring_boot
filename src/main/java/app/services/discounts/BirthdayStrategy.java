package app.services.discounts;

import app.models.EventHasAuditorium;
import app.models.User;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.chrono.ChronoLocalDate;

@Service
public class BirthdayStrategy implements DiscountStrategy {

    private UserService userService;

    public BirthdayStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public double calculateDiscount(User user, EventHasAuditorium eventHasAuditorium, long amountOfTickets,
                                    double totalPrice) {
        if (!userService.exists(user) || user.getBirthday() == null) {
            return 0;
        }
        if (user.getBirthday().toLocalDate().getDayOfYear() == eventHasAuditorium.getAirDate()
                .toLocalDateTime().getDayOfYear()
                || (user.getBirthday().toLocalDate()
                .isBefore(ChronoLocalDate.from(eventHasAuditorium.getAirDate().toLocalDateTime().plusDays(6)))
                && user.getBirthday().toLocalDate()
                .isAfter(ChronoLocalDate.from(eventHasAuditorium.getAirDate().toLocalDateTime().minusDays(6))))) {
            return totalPrice * 0.05;
        }
        return 0;
    }
}
