package com.mitocode.reservation.model.reservation;

import com.mitocode.reservation.model.gymclass.GymClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mitocode.reservation.model.customer.TestCustomerIdFactory.randomCustomerId;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationTest {

    @Test
    @DisplayName("given a valid reservation, spots are reduced accordingly")
    void givenValidReservation_spotsAreReduced() throws NotEnoughSpotsAvailableException {
        GymClass gymClass = createTestClass(10, 10);

        Reservation reservation = new Reservation(gymClass, randomCustomerId(), 3);

        assertThat(gymClass.spotsAvailable()).isEqualTo(7);
    }

    @Test
    @DisplayName("given a reservation with insufficient spots, exception is thrown")
    void givenReservationWithInsufficientSpots_throwsException() {
        GymClass gymClass = createTestClass(10, 2);

        assertThrows(NotEnoughSpotsAvailableException.class, () -> new Reservation(gymClass, randomCustomerId(), 5));
    }

    @Test
    @DisplayName("given a reservation, confirm it and check status")
    void givenReservation_whenConfirmed_thenStatusIsConfirmed() throws NotEnoughSpotsAvailableException {
        GymClass gymClass = createTestClass(10, 10);

        Reservation reservation = new Reservation(gymClass, randomCustomerId(), 3);

        assertThat(reservation.status()).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    @DisplayName("given a reservation, cancel it and check status")
    void givenReservation_whenCancelled_thenStatusIsCancelled() throws NotEnoughSpotsAvailableException {
        GymClass gymClass = createTestClass(10, 10);

        Reservation reservation = new Reservation(gymClass, randomCustomerId(), 3);
        reservation.cancel();

        assertThat(reservation.status()).isEqualTo(ReservationStatus.CANCELLED);
        assertThat(gymClass.spotsAvailable()).isEqualTo(10);
    }

    @Test
    @DisplayName("given a negative spots value, exception is thrown")
    void givenNegativeSpots_throwsException() {
        GymClass gymClass = createTestClass(10, 10);

        assertThrows(IllegalArgumentException.class, () -> new Reservation(gymClass, randomCustomerId(), -1));
    }

}
