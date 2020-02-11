package app.controllers;

import app.models.Event;
import app.models.EventHasAuditorium;
import app.models.User;
import app.models.enums.Rating;
import app.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventHasAuditoriumService eventHasAuditoriumService;
    @Autowired
    private AuditoriumService auditoriumService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/event")
    public String showEvent(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("rating", Arrays.stream(Rating.values()).collect(Collectors.toList()));
        return "addEvent";
    }

    @GetMapping("/main")
    public String main() {
        return "/admin/main";
    }

    @PostMapping("/event")
    public String addEvent(@ModelAttribute Event event) {
        eventService.save(event);
        return "main";
    }

    @GetMapping("/schedule/event")
    public String scheduleEvent(Model model) {
        model.addAttribute("events", eventService.getAll());
        model.addAttribute("auditoriums", auditoriumService.getAll());
        model.addAttribute("eventHasAuditorium", new EventHasAuditorium());
        return "scheduleEvent";
    }

    @PostMapping("/schedule/event")
    public String doScheduleEvent(@ModelAttribute EventHasAuditorium eventHasAuditorium) {
        eventHasAuditoriumService.save(eventHasAuditorium);
        return "scheduleEvent";
    }

    @GetMapping("/upload")
    public String uploadMultipleFiles() {
        return "batch";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> doUploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (!(files[0]).isEmpty()) {
            List<User> users = objectMapper.readValue(files[0].getBytes(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            userService.saveAll(users);
            stringBuilder.append(users.size() +" users were added");
        }
        if (!(files[1]).isEmpty()) {
            List<Event> events = objectMapper.readValue(files[1].getBytes(), objectMapper.getTypeFactory().constructCollectionType(List.class, Event.class));
            eventService.saveAll(events);
            stringBuilder.append("\n" +" events were added");
        }
        return ResponseEntity.ok(stringBuilder.toString());
    }

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    @PostMapping("/tickets/{eventId}")
    public String getTicketsForEvent(@PathVariable("eventId") long eventId, Model model) {
        model.addAttribute("ticketsList", ticketService.findAllByEventHasAuditorium_Event_Id(eventId));
        return "findTicketsForEvent";
    }

}
