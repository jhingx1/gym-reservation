package com.mitocode.reservation.application.port.in.reservation;

import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.ReservationNotFoundException;

public interface CancelReservationUseCase {

    void cancelReservation(CustomerId customerId, ClassId classId) throws ReservationNotFoundException;
}
