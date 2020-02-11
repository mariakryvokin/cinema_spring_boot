package app.repositories;

import app.models.Role;
import app.models.Ticket;
import app.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void countBookedTicketByUserId() {
    }

    @Test
    void getAllByAuditoriumNameAndAirDateTime() {
        Assertions.assertEquals(
                ticketRepository.getAllByEventHasAuditorium_Auditorium_NameAndEventHasAuditorium_AirDate("Green", Timestamp.valueOf("2020-02-02 00:00:00")).size(),2);
    }

    @Test
    void findAllByUser_IdAndOrder_Id() {
    }

    @Test
    void findAllByEventHasAuditorium_Event_Id() {

    }

    @Test
    void findAllByOrder() {
    }
}