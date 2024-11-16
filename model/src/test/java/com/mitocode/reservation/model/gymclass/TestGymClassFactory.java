package com.mitocode.reservation.model.gymclass;

public class TestGymClassFactory {

    private static final int ENOUGH_SPOTS_AVAILABLE = Integer.MAX_VALUE;
    private static final int DEFAULT_CAPACITY = Integer.MAX_VALUE;

    public static GymClass createTestClass() {
        return createTestClass(DEFAULT_CAPACITY, ENOUGH_SPOTS_AVAILABLE);
    }

    public static GymClass createTestClass(int capacity, int spotsAvailable) {
        return new GymClass(
                ClassId.randomClassId(),
                "Yoga Class",
                "An introductory yoga class",
                capacity,
                spotsAvailable
        );
    }
}
