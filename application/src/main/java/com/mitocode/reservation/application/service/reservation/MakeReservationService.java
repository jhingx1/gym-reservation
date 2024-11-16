package com.mitocode.reservation.application.service.reservation;

import com.mitocode.reservation.application.port.in.reservation.MakeReservationUseCase;
import com.mitocode.reservation.application.port.in.reservation.GymClassNotFoundException;
import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class MakeReservationService implements MakeReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final GymClassRepository gymClassRepository;

    @Override
    public Reservation makeReservation(CustomerId customerId, ClassId classId, int quantity)
            throws NotEnoughSpotsAvailableException, GymClassNotFoundException {
        Objects.requireNonNull(customerId, "'customerId' must not be null");
        Objects.requireNonNull(classId, "'classId' must not be null");

        if (quantity < 1) {
            throw new IllegalArgumentException("'quantity' must be greater than 0");
        }

        GymClass gymClass = gymClassRepository.findById(classId).orElseThrow(GymClassNotFoundException::new);

        if (gymClass.spotsAvailable() < quantity) {
            throw new NotEnoughSpotsAvailableException(
                    "Not enough spots available for class " + classId,
                    gymClass.spotsAvailable());
        }

        Reservation reservation = new Reservation(gymClass, customerId, quantity);
        gymClassRepository.save(reservation.gymClass());
        reservationRepository.save(reservation);

        return reservation;
    }
}
