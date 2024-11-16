package com.mitocode.reservation.application.port.in.gymclass;

import com.mitocode.reservation.model.gymclass.GymClass;

import java.util.List;

public interface FindGymClassUseCase {

    List<GymClass> findByTypeOrDescription(String query);

}
