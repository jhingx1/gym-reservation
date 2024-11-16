package com.mitocode.reservation.adapter.out.persistence;

import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.gymclass.ClassId;

import java.util.List;

public final class DemoGymClasses {

    public static final GymClass YOGA_CLASS =
            new GymClass(
                    new ClassId("GYM12345"),
                    "Yoga",
                    "A calming and rejuvenating yoga session for all skill levels",
                    30,
                    30
            );

    public static final GymClass SPINNING_CLASS =
            new GymClass(
                    new ClassId("GYM67890"),
                    "Spinning",
                    "High-energy cycling class for a full body workout",
                    20,
                    20
            );

    public static final GymClass PILATES_CLASS =
            new GymClass(
                    new ClassId("GYM11223"),
                    "Pilates",
                    "Strengthen your core with pilates exercises",
                    25,
                    25
            );

    public static final GymClass ZUMBA_CLASS =
            new GymClass(
                    new ClassId("GYM44556"),
                    "Zumba",
                    "Dance and fitness class to upbeat music",
                    40,
                    40
            );

    public static final List<GymClass> DEMO_GYM_CLASSES =
            List.of(YOGA_CLASS, SPINNING_CLASS, PILATES_CLASS, ZUMBA_CLASS);

    private DemoGymClasses() {}
}