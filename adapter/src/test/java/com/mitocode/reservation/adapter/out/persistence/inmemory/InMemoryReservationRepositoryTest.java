package com.mitocode.reservation.adapter.out.persistence.inmemory;

import com.mitocode.reservation.adapter.out.persistence.AbstractReservationRepositoryTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class InMemoryReservationRepositoryTest extends AbstractReservationRepositoryTest {
}