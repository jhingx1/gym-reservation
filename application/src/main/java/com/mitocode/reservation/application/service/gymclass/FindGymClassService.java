package com.mitocode.reservation.application.service.gymclass;

import com.mitocode.reservation.application.port.in.gymclass.FindGymClassUseCase;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.gymclass.GymClass;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class FindGymClassService implements FindGymClassUseCase {

    private final GymClassRepository gymClassRepository;

    @Override
    public List<GymClass> findByTypeOrDescription(String query) {
        Objects.requireNonNull(query, "'query' must not be null");
        if (query.length() < 2) {
            throw new IllegalArgumentException("'query' must be at least two characters long");
        }
        return gymClassRepository.findByTypeOrDescription(query);
    }

}
