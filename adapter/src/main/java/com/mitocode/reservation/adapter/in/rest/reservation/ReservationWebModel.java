package com.mitocode.reservation.adapter.in.rest.reservation;

import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import com.mitocode.reservation.model.reservation.ReservationStatus;

public record ReservationWebModel(
        ClassId classId,
        String classType,
        String classDescription,
        CustomerId customerId,
        int spotsReserved,
        ReservationStatus status
) {
    public static ReservationWebModel fromDomainModel(Reservation reservation) {
        return new ReservationWebModel(
                reservation.gymClass().id(),
                reservation.gymClass().type(),
                reservation.gymClass().description(),
                reservation.customerId(),
                reservation.spotsReserved(),
                reservation.status()
        );
    }
}
