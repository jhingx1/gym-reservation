package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.reservation.Reservation;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.reservation.ReservationStatus;

import java.util.List;
import java.util.Optional;

public class ReservationMapper {

    private ReservationMapper() {}

    static ReservationJpaEntity toJpaEntity(Reservation reservation) {
        ReservationJpaEntity reservationJpaEntity = new ReservationJpaEntity();

        reservationJpaEntity.setCustomerId(reservation.customerId().email());
        reservationJpaEntity.setSpotsReserved(reservation.spotsReserved());
        reservationJpaEntity.setStatus(ReservationStatus.valueOf(reservation.status().name()));

        GymClassJpaEntity gymClassJpaEntity = new GymClassJpaEntity();
        gymClassJpaEntity.setId(reservation.gymClass().id().value());
        reservationJpaEntity.setGymClass(gymClassJpaEntity);

        return reservationJpaEntity;
    }

    static Optional<Reservation> toModelEntityOptional(ReservationJpaEntity reservationJpaEntity) {
        if (reservationJpaEntity == null) {
            return Optional.empty();
        }

        GymClass gymClass = GymClassMapper.toModelEntity(reservationJpaEntity.getGymClass());
        ReservationStatus status = ReservationStatus.valueOf(reservationJpaEntity.getStatus().name());

        Reservation reservation = new Reservation(
                gymClass,
                new CustomerId(reservationJpaEntity.getCustomerId()),
                reservationJpaEntity.getSpotsReserved(),
                status
        );

        return Optional.of(reservation);
    }

    static List<Reservation> toModelEntities(List<ReservationJpaEntity> reservationJpaEntities) {
        return reservationJpaEntities.stream()
                .map(ReservationMapper::toModelEntityOptional)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
