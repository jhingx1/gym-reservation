package com.mitocode.reservation.application.port.out.persistence;

import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    void save(Reservation reservation);

    void deleteByCustomerId(CustomerId customerId);

    void deleteReservationByCustomerIdAndClassId(CustomerId customerId, ClassId classId);

    List<Reservation> findReservationsByCustomerId(CustomerId customerId);

    Optional<Reservation> findByCustomerIdAndClassId(CustomerId customerId, ClassId classId);

}
