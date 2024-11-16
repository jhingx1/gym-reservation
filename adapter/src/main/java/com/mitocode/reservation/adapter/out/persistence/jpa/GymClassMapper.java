package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.gymclass.ClassId;

import java.util.List;
import java.util.Optional;

public class GymClassMapper {

    private GymClassMapper(){}

    static GymClassJpaEntity toJpaEntity(GymClass aGymClass){
        GymClassJpaEntity jpaEntity = new GymClassJpaEntity();

        jpaEntity.setId(aGymClass.id().value());
        jpaEntity.setType(aGymClass.type());
        jpaEntity.setDescription(aGymClass.description());
        jpaEntity.setCapacity(aGymClass.capacity());
        jpaEntity.setSpotsAvailable(aGymClass.spotsAvailable());

        return jpaEntity;
    }

    static Optional<GymClass> toModelEntityOptional(GymClassJpaEntity jpaEntity){
        return Optional.ofNullable(jpaEntity).map(GymClassMapper::toModelEntity);
    }

    static GymClass toModelEntity(GymClassJpaEntity jpaEntity){
        return new GymClass(
                new ClassId(jpaEntity.getId()),
                jpaEntity.getType(),
                jpaEntity.getDescription(),
                jpaEntity.getCapacity(),
                jpaEntity.getSpotsAvailable()
        );
    }

    static List<GymClass> toModelEntities(List<GymClassJpaEntity> jpaEntities){
        return jpaEntities.stream().map(GymClassMapper::toModelEntity).toList();
    }
}
