package com.mitocode.reservation.application.service.reservation;

import com.mitocode.reservation.application.port.in.reservation.CancelReservationUseCase;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import com.mitocode.reservation.model.reservation.ReservationNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CancelReservationService implements CancelReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final GymClassRepository gymClassRepository;

    @Override
    public void cancelReservation(CustomerId customerId, ClassId classId) throws ReservationNotFoundException {
        Objects.requireNonNull(customerId, "'customerId' must not be null");
        Objects.requireNonNull(classId, "'classId' must not be null");

        Reservation reservation = reservationRepository.findByCustomerIdAndClassId(customerId, classId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        reservation.cancel();

        gymClassRepository.save(reservation.gymClass());
        reservationRepository.deleteReservationByCustomerIdAndClassId(customerId, classId);
    }
}
