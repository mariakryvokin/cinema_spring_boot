package app.controllers;

import app.models.Role;
import app.models.User;
import app.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(authorities = "RESGISTERED_USER")
    @Transactional
    void saveUser() throws Exception {
        String email = "test@gmail.com";
        User user = User.builder()
                .email(email)
                .roles(Collections.singletonList(new Role("ADMIN")))
                .password("password").birthday(Date.valueOf(LocalDate.now()))
                .lastName("test")
                .lastName("test")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/save")
                .flashAttr("user",user))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertTrue(userRepository.getUserByEmail(email) != null);
    }

    @Test
    void doCart() {
    }

    @Test
    void cart() {
    }
}