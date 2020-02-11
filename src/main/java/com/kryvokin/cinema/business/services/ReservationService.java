package com.kryvokin.cinema.business.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.kryvokin.cinema.business.domain.RoomReservation;
import com.kryvokin.cinema.data.entity.Guest;
import com.kryvokin.cinema.data.entity.Reservation;
import com.kryvokin.cinema.data.entity.Room;
import com.kryvokin.cinema.data.repository.GuestRepository;
import com.kryvokin.cinema.data.repository.ReservationRepository;
import com.kryvokin.cinema.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(LocalDate date){
        ArrayList<RoomReservation> roomReservations = new ArrayList<>();
        List<Reservation> reservations = (List<Reservation>) reservationRepository.getReservationsByDate(date);
        Map<Long,Room> rooms = roomRepository.findAll().stream().collect(Collectors.toMap(r->r.getId(),Function.identity()));
        reservations.forEach(reservation -> {
            Guest guest = guestRepository.findById(reservation.getGuestId()).get();
            roomReservations.add(new RoomReservation(reservation.getRoomId(),reservation.getGuestId(),rooms.get(reservation.getRoomId()).getName(),
                    rooms.get(reservation.getRoomId()).getNumber(), guest.getFirstName(),
                    guest.getLastName(), date));
        });
        return roomReservations;
    }
}
