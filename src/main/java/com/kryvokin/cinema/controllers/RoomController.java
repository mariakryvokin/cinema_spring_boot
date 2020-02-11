package com.kryvokin.cinema.controllers;

import com.kryvokin.cinema.business.domain.RoomReservation;
import com.kryvokin.cinema.business.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${test.app}")
    private String test;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation/{date}") //2020-01-01  LocalDate.parse("2020-01-01")
    public List<RoomReservation> getRoomReservationsForDate(@PathVariable("date")String date){
        return reservationService.getRoomReservationsForDate(LocalDate.parse(date));
    }

}
