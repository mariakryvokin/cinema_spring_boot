package com.kryvokin.cinema.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryvokin.cinema.business.domain.RoomReservation;
import com.kryvokin.cinema.business.services.ReservationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc
class RoomControllerTest {
    @LocalServerPort
    private int port;
    @MockBean
    private ReservationService reservationService;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getRoomReservationsForDate() throws Exception {
        List<RoomReservation> roomReservations = new ArrayList<>();
        roomReservations.add(new RoomReservation());
        Mockito.when(reservationService.getRoomReservationsForDate(ArgumentMatchers.any(LocalDate.class))).thenReturn(roomReservations);
        this.mockMvc.perform(get("/reservation/{date}","2020-01-01")).andExpect(status().isOk()).andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$..roomId").exists());
        ResponseEntity<String> stringResponseEntity = testRestTemplate.getForEntity("http://localhost:"+port+"/reservation/2020-01-01",String.class);
        Assert.assertTrue(objectMapper.readTree(stringResponseEntity.getBody()).elements().next().has("roomId"));
    }

    @ParameterizedTest
    public void parametrizedtest(){

    }


}