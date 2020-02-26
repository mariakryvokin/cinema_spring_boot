package app.controllers;

import app.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(authorities = "ADMIN")
    @Transactional
    public void uploadMultipleFiles() throws Exception {
        MultipartFile[] multipartFiles = FileUtils.getMultipartFiles();
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders
                .multipart("/admin/upload").file((MockMultipartFile) multipartFiles[0]).file((MockMultipartFile) multipartFiles[1]))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.contains("users were added") && !contentAsString.contains("events"));

        Assertions.assertTrue(userRepository.getUserByEmail("TestUser@gmail.com") != null);
    }


    @Test
    @WithMockUser(authorities = "BOOKING_MANAGER")
    public void getTicketsForEvent_303() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/tickets/{eventId}", 1L)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }


}