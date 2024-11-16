package com.mitocode.reservation.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.gymclass.ClassId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractGymClassRepositoryTest<T extends GymClassRepository> {

    private T gymClassRepository;

    @BeforeEach
    void initRepository() {
        gymClassRepository = createGymClassRepository();
    }

    protected abstract T createGymClassRepository();

    @Test
    void givenTestGymClassesAndATestClassId_findById_returnsATestGymClass() {
        ClassId classId = DemoGymClasses.YOGA_CLASS.id();

        Optional<GymClass> gymClass = gymClassRepository.findById(classId);

        assertThat(gymClass).contains(DemoGymClasses.YOGA_CLASS);
    }

    @Test
    void givenTheIdOfAGymClassNotPersisted_findById_returnsAnEmptyOptional() {
        ClassId classId = new ClassId("00000");

        Optional<GymClass> gymClass = gymClassRepository.findById(classId);

        assertThat(gymClass).isEmpty();
    }

    @Test
    void givenTestGymClassesAndASearchQueryNotMatchingAnyGymClass_findByTypeOrDescription_returnsAnEmptyList() {
        String query = "not matching any gym class";

        List<GymClass> gymClasses = gymClassRepository.findByTypeOrDescription(query);

        assertThat(gymClasses).isEmpty();
    }

    @Test
    void givenTestGymClassesAndASearchQueryMatchingOneGymClass_findByTypeOrDescription_returnsThatGymClass() {
        String query = "yoga";

        List<GymClass> gymClasses = gymClassRepository.findByTypeOrDescription(query);

        assertThat(gymClasses).containsExactlyInAnyOrder(DemoGymClasses.YOGA_CLASS);
    }

    @Test
    void givenTestGymClassesAndASearchQueryMatchingMultipleGymClasses_findByTypeOrDescription_returnsThoseGymClasses() {
        String query = "class";

        List<GymClass> gymClasses = gymClassRepository.findByTypeOrDescription(query);

        assertThat(gymClasses)
                .containsExactlyInAnyOrder(DemoGymClasses.SPINNING_CLASS, DemoGymClasses.ZUMBA_CLASS);
    }
}