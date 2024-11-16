package com.mitocode.reservation.adapter.in.rest.gymclass;

import com.mitocode.reservation.model.gymclass.GymClass;

public record GymClassInListWebModel(String id, String type, String description, int capacity, int spotsAvailable) {

    public static GymClassInListWebModel fromDomainModel(GymClass aGymClass) {
        System.out.println(aGymClass);
        return new GymClassInListWebModel(
                aGymClass.id().value(),
                aGymClass.type(),
                aGymClass.description(),
                aGymClass.capacity(),
                aGymClass.spotsAvailable()
        );
    }
}