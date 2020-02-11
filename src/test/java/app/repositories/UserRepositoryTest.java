package app.repositories;

import app.models.*;
import app.models.enums.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void sutUp(){
        Role role = new Role("TestADMIN");
        testEntityManager.persist(role);

        User user = User.builder()
                .firstName("TestFN")
                .lastName("TestLS")
                .birthday(Date.valueOf(LocalDate.now()))
                .email("Test@gmail.com")
                .password("TestPassword")
                .roles(Arrays.asList(new Role("TestADMIN")))
                .build();
        testEntityManager.persist(user);

        Auditorium auditorium = Auditorium.builder()
                .amountOfSeats(100)
                .name("TestGreen")
                .vipSeats(null)
                .build();

        testEntityManager.persist(auditorium);

        Event event = Event.builder()
                .basePrice(100.0)
                .name("TestEvent")
                .rating(Rating.HIGH)
                .build();

        testEntityManager.persist(event);

        EventHasAuditorium eventHasAuditorium = EventHasAuditorium.builder()
                .auditorium(auditorium)
                .event(event)
                .airDate(Timestamp.valueOf(LocalDateTime.now().plusDays(10)))
                .build();

        testEntityManager.persist(eventHasAuditorium);

        Order order = Order.builder()
                .price(100.0)
                .user(user)
                .build();

        testEntityManager.persist(order);

        Ticket ticket = Ticket.builder()
                .seat(2)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .user(user)
                .eventHasAuditorium(eventHasAuditorium)
                .order(order)
                .build();

        testEntityManager.persist(ticket);

    }

    @Test
    void getUserByEmail() {
        User testUser = userRepository.getUserByEmail("FUemail@gmail.com");
        Assertions.assertTrue(testUser.getId() != null);
    }

    @Test
    void countBookedTickets() {
        int userTickets = userRepository.countBookedTickets(userRepository.getUserByEmail("Test@gmail.com").getId());
        Assertions.assertTrue(userTickets == 1 );
    }
}