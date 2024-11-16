package com.mitocode.reservation.adapter.out.persistence.jpa;

import com.mitocode.reservation.adapter.out.persistence.DemoGymClasses;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.gymclass.GymClass;
import com.mitocode.reservation.model.gymclass.ClassId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class JpaGymClassRepository implements GymClassRepository {

    private final EntityManagerFactory entityManagerFactory;

    public JpaGymClassRepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        createDemoGymClasses();
    }

    private void createDemoGymClasses(){
        DemoGymClasses.DEMO_GYM_CLASSES.forEach(this::save);
    }

    @Override
    public void save(GymClass gymClass){
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.merge(GymClassMapper.toJpaEntity(gymClass));
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Optional<GymClass> findById(ClassId classId){
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            GymClassJpaEntity gymClassJpaEntity = entityManager.find(GymClassJpaEntity.class, classId.value());
            return GymClassMapper.toModelEntityOptional(gymClassJpaEntity);
        }
    }

    @Override
    public List<GymClass> findByTypeOrDescription(String queryString){
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            TypedQuery<GymClassJpaEntity> query =
                    entityManager.createQuery(
                                    "from GymClassJpaEntity where type like :query or description like :query",
                                    GymClassJpaEntity.class
                            )
                            .setParameter("query", "%" + queryString + "%");

            List<GymClassJpaEntity> entities = query.getResultList();
            return GymClassMapper.toModelEntities(entities);
        }
    }

}
