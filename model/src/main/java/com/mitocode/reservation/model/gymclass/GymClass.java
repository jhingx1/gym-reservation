package com.mitocode.reservation.model.gymclass;

import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class GymClass {
    private final ClassId id;
    private String type;
    private String description;
    private int capacity;
    private int spotsAvailable;

    public void reserveSpots(int spots) throws NotEnoughSpotsAvailableException {
        if (spots > spotsAvailable) {
            throw new NotEnoughSpotsAvailableException("Not enough spots available", spotsAvailable);
        }
        spotsAvailable -= spots;
    }

    public void releaseSpots(int spots) {
        spotsAvailable += spots;
    }
}
