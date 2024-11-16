package com.mitocode.reservation.application.port.out.persistence;

import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.gymclass.ClassId;

import java.util.List;
import java.util.Optional;

public interface GymClassRepository {

    List<GymClass> findByTypeOrDescription(String query);

    Optional<GymClass> findById(ClassId classId);

    void save(GymClass aGymClass);

}
