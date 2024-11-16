package com.mitocode.reservation.application.service.reservation;

import static com.mitocode.reservation.model.customer.TestCustomerIdFactory.randomCustomerId;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.reservation.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

public class GetUserReservationsServiceTest {

    private static final CustomerId TEST_CUSTOMER_ID = randomCustomerId();
    private static final GymClass TEST_GYM_CLASS_1 = createTestClass(30, 20);

    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
    private final GetUserReservationsService getUserReservationsService = new GetUserReservationsService(reservationRepository);

    @Test
    @DisplayName("given reservation is persisted and get reservation returns persisted reservation")
    void test1() throws NotEnoughSpotsAvailableException {
        Reservation persistedReservation = new Reservation(TEST_GYM_CLASS_1, TEST_CUSTOMER_ID, 2);

        Mockito.when(reservationRepository.findReservationsByCustomerId(TEST_CUSTOMER_ID))
                .thenReturn(List.of(persistedReservation));

        List<Reservation> persistedResult = getUserReservationsService.getReservations(TEST_CUSTOMER_ID);

        assertThat(persistedResult).contains(persistedReservation);
    }

    @Test
    @DisplayName("given reservation is not persisted")
    void test2 (){
        Mockito.when(reservationRepository.findReservationsByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Collections.emptyList());

        List<Reservation> reservations = getUserReservationsService.getReservations(TEST_CUSTOMER_ID);

        assertThat(reservations).isNotNull();
        assertThat(reservations).isEmpty();
    }
}
