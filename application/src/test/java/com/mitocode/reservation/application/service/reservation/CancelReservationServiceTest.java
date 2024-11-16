package com.mitocode.reservation.application.service.reservation;

import static com.mitocode.reservation.model.customer.TestCustomerIdFactory.randomCustomerId;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static org.mockito.Mockito.*;

import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import com.mitocode.reservation.model.reservation.Reservation;
import com.mitocode.reservation.model.reservation.ReservationNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class CancelReservationServiceTest {
    private static final CustomerId TEST_CUSTOMER_ID = randomCustomerId();
    private static final GymClass TEST_GYM_CLASS = createTestClass(30, 20);

    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
    private final GymClassRepository gymClassRepository = mock(GymClassRepository.class);

    private final CancelReservationService cancelReservationService =
            new CancelReservationService(reservationRepository, gymClassRepository);

    @Test
    @DisplayName("empty reservation invoke delete on the persistence port")
    void test1() throws ReservationNotFoundException, NotEnoughSpotsAvailableException {
        Reservation testReservation = new Reservation(TEST_GYM_CLASS, TEST_CUSTOMER_ID, 1);

        when(reservationRepository.findByCustomerIdAndClassId(TEST_CUSTOMER_ID, TEST_GYM_CLASS.id()))
                .thenReturn(Optional.of(testReservation));

        doNothing().when(reservationRepository).deleteReservationByCustomerIdAndClassId(
                TEST_CUSTOMER_ID, TEST_GYM_CLASS.id());

        cancelReservationService.cancelReservation(TEST_CUSTOMER_ID, TEST_GYM_CLASS.id());

        verify(reservationRepository).deleteReservationByCustomerIdAndClassId(TEST_CUSTOMER_ID, TEST_GYM_CLASS.id());
    }

}
