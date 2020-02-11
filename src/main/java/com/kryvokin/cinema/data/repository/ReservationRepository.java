package com.kryvokin.cinema.data.repository;

import com.kryvokin.cinema.data.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Iterable<Reservation> getReservationsByDate(LocalDate localDate);
}
