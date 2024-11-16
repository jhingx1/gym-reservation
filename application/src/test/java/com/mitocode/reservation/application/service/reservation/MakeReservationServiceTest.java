package com.mitocode.reservation.application.service.reservation;

import com.mitocode.reservation.application.port.in.reservation.GymClassNotFoundException;
import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import com.mitocode.reservation.model.reservation.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.mitocode.reservation.model.customer.TestCustomerIdFactory.randomCustomerId;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MakeReservationServiceTest {

    private static final CustomerId TEST_CUSTOMER_ID = randomCustomerId();
    private static final GymClass TEST_GYM_CLASS_1 = createTestClass(30, 20);
    private static final GymClass TEST_GYM_CLASS_2 = createTestClass(10, 10);

    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
    private final GymClassRepository gymClassRepository = mock(GymClassRepository.class);

    private final MakeReservationService makeReservationService =
            new MakeReservationService(reservationRepository, gymClassRepository);

    @BeforeEach
    void initTestDouble(){
        when(gymClassRepository.findById(TEST_GYM_CLASS_1.id())).thenReturn(Optional.of(TEST_GYM_CLASS_1));
        when(gymClassRepository.findById(TEST_GYM_CLASS_2.id())).thenReturn(Optional.of(TEST_GYM_CLASS_2));
    }

    @Test
    @DisplayName("given existing cart")
    void test1() throws NotEnoughSpotsAvailableException, GymClassNotFoundException {
        Reservation reservation = makeReservationService.makeReservation(TEST_CUSTOMER_ID, TEST_GYM_CLASS_1.id(), 2);

        verify(reservationRepository).save(reservation);

        assertThat(reservation.gymClass()).isEqualTo(TEST_GYM_CLASS_1);
        assertThat(reservation.spotsReserved()).isEqualTo(2);
        assertThat(reservation.status()).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    @DisplayName("given gym class with insufficient spots, throw exception")
    void testMakeReservation_InsufficientSpots() {
        int spotsRequested = 30;
        assertThatThrownBy(() -> makeReservationService.makeReservation(TEST_CUSTOMER_ID, TEST_GYM_CLASS_2.id(), spotsRequested))
                .isInstanceOf(NotEnoughSpotsAvailableException.class)
                .hasMessageContaining("Not enough spots available");
    }

    @Test
    @DisplayName("given unknown gym class id, throw exception")
    void testMakeReservation_UnknownClass() {
        ClassId unknownClassId = ClassId.randomClassId();

        assertThatThrownBy(() -> makeReservationService.makeReservation(TEST_CUSTOMER_ID, unknownClassId, 1))
                .isInstanceOf(GymClassNotFoundException.class);
    }

    @Test
    @DisplayName("given invalid quantity (less than 1), throw exception")
    void testMakeReservation_InvalidQuantity() {
        int invalidQuantity = 0;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> makeReservationService.makeReservation(TEST_CUSTOMER_ID, TEST_GYM_CLASS_1.id(), invalidQuantity))
                .withMessageContaining("'quantity' must be greater than 0");
    }

}
