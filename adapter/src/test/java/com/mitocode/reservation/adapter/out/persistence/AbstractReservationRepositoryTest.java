package com.mitocode.reservation.adapter.out.persistence;

import static com.mitocode.reservation.model.customer.TestCustomerIdFactory.randomCustomerId;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static org.assertj.core.api.Assertions.assertThat;

import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.reservation.Reservation;
import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.GymClass;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractReservationRepositoryTest<T extends ReservationRepository, U extends GymClassRepository> {

    private static final GymClass TEST_GYM_CLASS_1 = createTestClass(10, 10);
    private static final GymClass TEST_GYM_CLASS_2 = createTestClass(5, 5);

    private T reservationRepository;
    private U gymClassRepository;

    @BeforeEach
    void initRepositories() {
        reservationRepository = createReservationRepository();
        gymClassRepository = createGymClassRepository();
        persistTestGymClasses();
    }

    protected abstract T createReservationRepository();

    protected abstract U createGymClassRepository();

    private void persistTestGymClasses() {
        gymClassRepository.save(TEST_GYM_CLASS_1);
        gymClassRepository.save(TEST_GYM_CLASS_2);
    }

    @Test
    void givenACustomerIdForWhichNoReservationIsPersisted_findReservationsByCustomerId_returnsAnEmptyList() {
        CustomerId customerId = randomCustomerId();

        List<Reservation> reservations = reservationRepository.findReservationsByCustomerId(customerId);

        assertThat(reservations).isEmpty();
    }

    @Test
    void givenPersistedReservationWithGymClass_findReservationsByCustomerId_returnsTheAppropriateReservation()
            throws NotEnoughSpotsAvailableException {
        CustomerId customerId = randomCustomerId();

        Reservation persistedReservation = new Reservation(TEST_GYM_CLASS_1, customerId, 1); // Reservando 1 lugar
        reservationRepository.save(persistedReservation);

        List<Reservation> reservations = reservationRepository.findReservationsByCustomerId(customerId);

        assertThat(reservations).isNotEmpty();
        assertThat(reservations.get(0).gymClass()).isEqualTo(TEST_GYM_CLASS_1);
        assertThat(reservations.get(0).spotsReserved()).isEqualTo(1);
    }

    @Test
    void
    givenExistingReservationForACustomer_whenAddingNewReservation_updatesTheExistingReservation()
            throws NotEnoughSpotsAvailableException {
        CustomerId customerId = randomCustomerId();

        Reservation existingReservation = new Reservation(TEST_GYM_CLASS_1, customerId, 1);
        reservationRepository.save(existingReservation);

        Reservation updatedReservation = new Reservation(TEST_GYM_CLASS_1, customerId, 2);
        reservationRepository.save(updatedReservation);

        List<Reservation> reservations = reservationRepository.findReservationsByCustomerId(customerId);

        assertThat(reservations).isNotEmpty();
        assertThat(reservations.get(0).spotsReserved()).isEqualTo(1);
    }

    @Test
    void givenExistingReservation_deleteReservationByCustomerIdAndClassId_deletesTheReservation() throws NotEnoughSpotsAvailableException {
        CustomerId customerId = randomCustomerId();

        Reservation reservation = new Reservation(TEST_GYM_CLASS_1, customerId, 1);
        reservationRepository.save(reservation);

        assertThat(reservationRepository.findReservationsByCustomerId(customerId)).isNotEmpty();

        reservationRepository.deleteReservationByCustomerIdAndClassId(customerId, TEST_GYM_CLASS_1.id());

        assertThat(reservationRepository.findReservationsByCustomerId(customerId)).isEmpty();
    }

    @Test
    void givenNotExistingReservation_deleteReservationByCustomerIdAndClassId_doesNothing() {
        CustomerId customerId = randomCustomerId();
        assertThat(reservationRepository.findReservationsByCustomerId(customerId)).isEmpty();

        reservationRepository.deleteReservationByCustomerIdAndClassId(customerId, TEST_GYM_CLASS_1.id());

        assertThat(reservationRepository.findReservationsByCustomerId(customerId)).isEmpty();
    }

}