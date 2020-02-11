package app.controllers;


import app.models.Ticket;
import app.models.dto.TicketPdfDto;
import app.services.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TicketRestController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private ObjectMapper objectMapper;

    private static Logger logger = LogManager.getLogger();

    @GetMapping(value = "/download/{id}", produces = "application/pdf")
    public ResponseEntity<TicketPdfDto> downloadTicket(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        if (ticket.isPresent()) {
            return new ResponseEntity<TicketPdfDto>(new TicketPdfDto(ticket.get().getId(), ticket.get().getSeat(), ticket.get().getTimestamp(),
                    ticket.get().getEventHasAuditorium().getEvent().getName(), ticket.get().getEventHasAuditorium().getAirDate(), ticket.get().getEventHasAuditorium().getAuditorium().getName()),
                    headers, HttpStatus.OK);
        }
        else{
            throw new RuntimeException("No ticket found by id: " + id);
        }
    }
}
