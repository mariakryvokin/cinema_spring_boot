package app.controllers;

import app.config.viewresolvers.PDFView;
import app.models.Role;
import app.models.User;
import app.models.enums.RoleEnum;
import app.services.AuthenticationSuccessHandlerImpl;
import app.services.TicketService;
import app.services.UserDetailsServiceImpl;
import app.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private TicketService ticketService;
    @MockBean
    private PDFView pdfView;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void main() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/main"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("id"));
    }

    @Test
    @WithMockUser(authorities = "RESGISTERED_USER")
    void saveUser() throws Exception {
        String password = "password";
        User user = User.builder().password(password)
                .roles(Collections.singletonList(new Role(RoleEnum.RESGISTERED_USER.name()))).build();
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);
        Mockito.when(userService.save(ArgumentMatchers.any(), ArgumentMatchers.anyList())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/save")
                .flashAttr("user", user))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("main"));
    }

    @Test
    @WithMockUser(authorities = "RESGISTERED_USER")
    void doCart() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setSingleView(pdfView).build();
        Mockito.when(userService.getUserByEmail(ArgumentMatchers.anyString()))
                .thenReturn(User.builder().id(1L).build());
        Mockito.when(ticketService.getCartTicketsByUserIdAndOrderId(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/cart")
                .accept(MediaType.APPLICATION_PDF_VALUE)
                .flashAttr("user", User.builder().email("test@gmial.com").build()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ticketsToBeBought"));
        Mockito.verify(userService).getUserByEmail(ArgumentMatchers.anyString());


    }

    @Test
    @WithMockUser(authorities = "RESGISTERED_USER")
    void cart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/cart")).andExpect(MockMvcResultMatchers.status().isOk());
    }

}