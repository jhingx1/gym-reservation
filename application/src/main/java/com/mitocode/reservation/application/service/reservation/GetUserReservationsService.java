package com.mitocode.reservation.application.service.reservation;

import com.mitocode.reservation.application.port.in.reservation.GetUserReservationsUseCase;
import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.reservation.Reservation;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class GetUserReservationsService implements GetUserReservationsUseCase {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getReservations(CustomerId customerId) {
        Objects.requireNonNull(customerId, "'customerId' must not be null");

        return reservationRepository.findReservationsByCustomerId(customerId);
    }
}
