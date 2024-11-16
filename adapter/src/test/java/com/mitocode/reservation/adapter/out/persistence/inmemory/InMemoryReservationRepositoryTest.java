package com.mitocode.reservation.adapter.out.persistence.inmemory;

import com.mitocode.reservation.adapter.out.persistence.AbstractReservationRepositoryTest;

class InMemoryReservationRepositoryTest
        extends AbstractReservationRepositoryTest<InMemoryReservationRepository, InMemoryGymClassRepository> {

    @Override
    protected InMemoryReservationRepository createReservationRepository() {
        return new InMemoryReservationRepository();
    }

    @Override
    protected InMemoryGymClassRepository createGymClassRepository() {
        return new InMemoryGymClassRepository();
    }
}