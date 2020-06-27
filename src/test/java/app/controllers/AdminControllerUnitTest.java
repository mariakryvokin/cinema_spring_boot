package app.controllers;

import app.models.Event;
import app.models.EventHasAuditorium;
import app.models.Ticket;
import app.models.User;
import app.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@WebMvcTest(AdminController.class)
class AdminControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdminController adminController;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    @MockBean
    private EventService eventService;
    @MockBean
    private EventHasAuditoriumService eventHasAuditoriumService;
    @MockBean
    private AuditoriumService auditoriumService;
    @MockBean
    private UserService userService;
    @MockBean
    private TicketService ticketService;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void mainGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/main"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addEventPostTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/event"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(eventService).save(ArgumentMatchers.any(Event.class));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addEventGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/event"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("event"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void scheduleEventTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/schedule/event"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("events"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("auditoriums"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("notExistingAttribute"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("eventHasAuditorium"))
                .andExpect(MockMvcResultMatchers.view().name("scheduleEvent"));
        Mockito.verify(eventService).getAll();
        Mockito.verify(auditoriumService).getAll();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void doUploadMultipleFiles() throws Exception {
        MockMultipartFile[] multipartFiles = FileUtils.getMultipartFiles();
        Mockito.when(userService.saveAll(ArgumentMatchers.anyList())).thenReturn(Arrays.asList(new User()));
        Mockito.when(eventService.saveAll(ArgumentMatchers.anyList())).thenReturn(Arrays.asList(new Event()));
        ResponseEntity<String> result = adminController.doUploadMultipleFiles(multipartFiles);
        Mockito.verify(eventService).saveAll(ArgumentMatchers.anyList());
        Assertions.assertTrue(result.getBody().contains("entities were saved"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void doScheduleEvent() throws Exception {
        Mockito.when(eventHasAuditoriumService.save(ArgumentMatchers.any(EventHasAuditorium.class)))
                .thenReturn(new EventHasAuditorium());
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/schedule/event"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(eventHasAuditoriumService, Mockito.times(1))
                .save(ArgumentMatchers.any(EventHasAuditorium.class));
    }

    @Test
    @WithMockUser(authorities = {"BOOKING_MANAGER", "ADMIN"})
    public void getTicketsForEvent() throws Exception {
        Mockito.when(ticketService.findAllByEventHasAuditorium_Event_Id(ArgumentMatchers.anyLong()))
                .thenReturn(Arrays.asList(new Ticket()));
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/tickets/{eventId}",Mockito.anyLong()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ticketsList"));
        Mockito.verify(ticketService,Mockito.atLeastOnce()).findAllByEventHasAuditorium_Event_Id(Mockito.anyLong());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void uploadMultipleFiles() throws Exception {
        String contentType = mockMvc.perform(MockMvcRequestBuilders.get("/admin/upload"))
                .andReturn().getResponse().getContentType();
        Assertions.assertTrue(contentType.contains(MediaType.TEXT_HTML.getSubtype()));
    }


}