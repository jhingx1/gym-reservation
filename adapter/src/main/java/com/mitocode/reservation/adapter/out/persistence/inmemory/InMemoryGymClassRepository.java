package com.mitocode.reservation.adapter.out.persistence.inmemory;

import com.mitocode.reservation.adapter.out.persistence.DemoGymClasses;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.gymclass.ClassId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "persistence", havingValue = "inmemory", matchIfMissing = true)
@Repository
public class InMemoryGymClassRepository implements GymClassRepository {

    private final Map<ClassId, GymClass> gymClasses = new ConcurrentHashMap<>();

    public InMemoryGymClassRepository() {
        createDemoGymClasses();
    }

    private void createDemoGymClasses() {
        DemoGymClasses.DEMO_GYM_CLASSES.forEach(this::save);
    }

    @Override
    public void save(GymClass gymClass) {
        gymClasses.put(gymClass.id(), gymClass);
    }

    @Override
    public Optional<GymClass> findById(ClassId classId) {
        return Optional.ofNullable(gymClasses.get(classId));
    }

    @Override
    public List<GymClass> findByTypeOrDescription(String query) {
        String queryLowerCase = query.toLowerCase(Locale.ROOT);

        return gymClasses.values().stream()
                .filter(gymClass -> matchesQuery(gymClass, queryLowerCase))
                .collect(Collectors.toList());
    }

    private boolean matchesQuery(GymClass gymClass, String query) {
        return gymClass.type().toLowerCase(Locale.ROOT).contains(query)
                || gymClass.description().toLowerCase(Locale.ROOT).contains(query);
    }

}
