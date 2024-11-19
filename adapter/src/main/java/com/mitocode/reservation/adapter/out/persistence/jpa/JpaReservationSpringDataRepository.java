package com.mitocode.reservation.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaReservationSpringDataRepository extends JpaRepository<ReservationJpaEntity, Long> {

    @Query("SELECT r FROM ReservationJpaEntity r WHERE r.customerId = ?1")
    List<ReservationJpaEntity> findByCustomerId (String pattern);

    @Query("SELECT r FROM ReservationJpaEntity r WHERE r.customerId = ?1 and r.gymClass.id = ?2")
    Optional<ReservationJpaEntity> findByCustomerIdAndClassId(String pattern1, String pattern2);

}