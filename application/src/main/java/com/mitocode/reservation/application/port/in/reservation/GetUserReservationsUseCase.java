package com.mitocode.reservation.application.port.in.reservation;

import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.reservation.Reservation;

import java.util.List;

public interface GetUserReservationsUseCase {

    List<Reservation> getReservations(CustomerId customerId);
}
