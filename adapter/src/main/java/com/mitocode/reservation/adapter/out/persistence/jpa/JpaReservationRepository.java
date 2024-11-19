package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "persistence", havingValue = "mysql")
@Repository
@RequiredArgsConstructor
public class JpaReservationRepository implements ReservationRepository {

    private final JpaReservationSpringDataRepository springDataRepository;

    @Override
    @Transactional
    public void save(Reservation reservation){
        springDataRepository.save(ReservationMapper.toJpaEntity(reservation));
    }

    @Override
    @Transactional
    public void deleteByCustomerId(CustomerId customerId){
        springDataRepository.findByCustomerId(customerId.email())
                .forEach(reservation -> springDataRepository.deleteById(reservation.getId()));
    }

    @Override
    @Transactional
    public void deleteReservationByCustomerIdAndClassId(CustomerId customerId, ClassId classId){
        springDataRepository.findByCustomerIdAndClassId(customerId.email(), classId.value())
                .ifPresent(reservation -> springDataRepository.deleteById(reservation.getId()));
    }

    @Override
    @Transactional
    public List<Reservation> findReservationsByCustomerId(CustomerId customerId){
        return ReservationMapper.toModelEntities(
                springDataRepository.findByCustomerId(customerId.email())
        );
    }

    @Override
    @Transactional
    public Optional<Reservation> findByCustomerIdAndClassId(CustomerId customerId, ClassId classId) {
        return springDataRepository.findByCustomerIdAndClassId(customerId.email(), classId.value())
                .flatMap(reservationJpaEntity -> ReservationMapper.toModelEntityOptional(reservationJpaEntity));
    }
}