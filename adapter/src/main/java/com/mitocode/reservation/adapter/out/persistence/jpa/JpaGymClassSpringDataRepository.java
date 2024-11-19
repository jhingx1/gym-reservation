package com.mitocode.reservation.adapter.out.persistence.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGymClassSpringDataRepository extends JpaRepository<GymClassJpaEntity, String> {

    @Query("SELECT g FROM GymClassJpaEntity g WHERE g.type like ?1 or g.description like ?1")
    List<GymClassJpaEntity> findByTypeOrDescriptionLike(String pattern);
}