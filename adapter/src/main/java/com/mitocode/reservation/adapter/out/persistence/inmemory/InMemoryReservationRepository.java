package com.mitocode.reservation.adapter.out.persistence.inmemory;

import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ConditionalOnProperty(name = "persistence", havingValue = "inmemory", matchIfMissing = true)
@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<CustomerId, List<Reservation>> reservationMap = new ConcurrentHashMap<>();

    @Override
    public void save(Reservation reservation) {
        reservationMap
                .computeIfAbsent(reservation.customerId(), k -> new java.util.ArrayList<>())
                .add(reservation);
    }

    @Override
    public void deleteByCustomerId(CustomerId customerId) {
        reservationMap.remove(customerId);
    }

    @Override
    public void deleteReservationByCustomerIdAndClassId(CustomerId customerId, ClassId classId) {
        List<Reservation> customerReservations = reservationMap.get(customerId);
        if (customerReservations != null) {
            customerReservations.removeIf(reservation -> reservation.gymClass().id().equals(classId));
        }
    }

    @Override
    public List<Reservation> findReservationsByCustomerId(CustomerId customerId) {
        return reservationMap.getOrDefault(customerId, List.of());
    }

    @Override
    public Optional<Reservation> findByCustomerIdAndClassId(CustomerId customerId, ClassId classId) {
        List<Reservation> customerReservations = reservationMap.get(customerId);
        if (customerReservations != null) {
            return customerReservations.stream()
                    .filter(reservation -> reservation.gymClass().id().equals(classId))
                    .findFirst();
        }
        return Optional.empty();
    }
}
