package com.mitocode.reservation.model.reservation;

import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.GymClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Reservation {

    private final GymClass gymClass;
    private final CustomerId customerId;
    private int spotsReserved;
    @Setter
    private ReservationStatus status = ReservationStatus.PENDING;

    public Reservation(GymClass gymClass, CustomerId customerId, int spotsReserved) throws NotEnoughSpotsAvailableException {
        if (spotsReserved <= 0) {
            throw new IllegalArgumentException("Spots reserved must be greater than zero");
        }
        this.gymClass = gymClass;
        this.customerId = customerId;
        this.spotsReserved = spotsReserved;
        confirm();
    }

    public void confirm() throws NotEnoughSpotsAvailableException {
        this.status = ReservationStatus.CONFIRMED;
        gymClass.reserveSpots(this.spotsReserved);
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
        gymClass.releaseSpots(this.spotsReserved);
    }
}
