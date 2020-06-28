package app.services;

import app.models.Auditorium;
import app.models.EventHasAuditorium;
import app.models.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private TicketService ticketService;
    @Mock
    private EventHasAuditoriumService eventHasAuditoriumService;
    @Mock
    private AuditoriumService auditoriumService;

    private  BookingService bookingService;

    @BeforeEach
    void init(){
        bookingService = new BookingService(ticketService, eventHasAuditoriumService, auditoriumService);
    }

    @ParameterizedTest
    @MethodSource("provideParametersForGetFreeSeatTest")
    void getFreeSeatTest(int seatsAmount, int freeSeats) {
        Auditorium auditorium = Auditorium.builder().name("Green").amountOfSeats(seatsAmount).build();
        EventHasAuditorium eventHasAuditorium = EventHasAuditorium.builder().auditorium(auditorium).build();
        Ticket firstTicket = Ticket.builder().id(1).seat(1L).eventHasAuditorium(eventHasAuditorium).build();
        Ticket secondTicket = Ticket.builder().id(1).seat(2L).eventHasAuditorium(eventHasAuditorium).build();
        List<Ticket> tickets = Arrays.asList(firstTicket,secondTicket);
        Mockito.lenient().when(ticketService.getAll()).thenReturn(tickets);
        Mockito.lenient().when(eventHasAuditoriumService.getAll()).thenReturn(Arrays.asList(eventHasAuditorium));
        Mockito.lenient().when(auditoriumService.getAllByNameIn(ArgumentMatchers.anyList()))
                .thenReturn(Arrays.asList(auditorium));

        Assertions.assertEquals(freeSeats,bookingService.getFreeSeats().get(eventHasAuditorium).size());
        Mockito.verify(ticketService).getAll();
        Mockito.verify(eventHasAuditoriumService).getAll();
        Mockito.verify(auditoriumService).getAllByNameIn(ArgumentMatchers.anyList());
    }

    private static Stream<Arguments> provideParametersForGetFreeSeatTest(){
        return Stream.of(Arguments.of(3,1),Arguments.of(2,0),Arguments.of(0,0));
    }

    @Test
    void getFreeSeatWithoutOccupiedSeatsTest() {
        Auditorium auditorium = Auditorium.builder().name("Green").amountOfSeats(3).build();
        EventHasAuditorium eventHasAuditorium = EventHasAuditorium.builder().auditorium(auditorium).build();
        Mockito.lenient().when(ticketService.getAll()).thenReturn(Collections.EMPTY_LIST);
        Mockito.lenient().when(eventHasAuditoriumService.getAll()).thenReturn(Arrays.asList(eventHasAuditorium));
        Mockito.lenient().when(auditoriumService.getAllByNameIn(ArgumentMatchers.anyList()))
                .thenReturn(Arrays.asList(auditorium));

        Assertions.assertEquals(3,bookingService.getFreeSeats().get(eventHasAuditorium).size());
        Mockito.verify(ticketService).getAll();
        Mockito.verify(eventHasAuditoriumService).getAll();
        Mockito.verify(auditoriumService).getAllByNameIn(ArgumentMatchers.anyList());
    }
}