package com.mitocode.reservation.application.port.in.reservation;

import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;

public interface MakeReservationUseCase {

    Reservation makeReservation(CustomerId customerId, ClassId classId, int quantity)
            throws NotEnoughSpotsAvailableException, GymClassNotFoundException;
}
