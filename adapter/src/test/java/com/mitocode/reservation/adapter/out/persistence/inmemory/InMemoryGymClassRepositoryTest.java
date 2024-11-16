package com.mitocode.reservation.adapter.out.persistence.inmemory;

import com.mitocode.reservation.adapter.out.persistence.AbstractGymClassRepositoryTest;

class InMemoryGymClassRepositoryTest
        extends AbstractGymClassRepositoryTest<InMemoryGymClassRepository> {

    @Override
    protected InMemoryGymClassRepository createGymClassRepository() {
        return new InMemoryGymClassRepository();
    }
}