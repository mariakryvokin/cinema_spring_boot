package app.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Timestamp;

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
        Assertions.assertEquals(ticketRepository
                .getAllByEventHasAuditorium_Auditorium_NameAndEventHasAuditorium_AirDate("Green",
                        Timestamp.valueOf("2020-02-02 00:00:00")).size(),2);
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