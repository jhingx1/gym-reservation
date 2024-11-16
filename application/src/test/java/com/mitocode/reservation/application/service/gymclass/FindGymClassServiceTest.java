package com.mitocode.reservation.application.service.gymclass;

import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.model.gymclass.GymClass;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindGymClassServiceTest {

    private static final GymClass TEST_GYM_CLASS_1 = createTestClass(30, 20);
    private static final GymClass TEST_GYM_CLASS_2 = createTestClass(40, 15);

    private final GymClassRepository gymClassRepository = mock(GymClassRepository.class);
    private final FindGymClassService findProductsService =
            new FindGymClassService(gymClassRepository);

    @Test
    @DisplayName("given a search query")
    void test1() {
        when(gymClassRepository.findByTypeOrDescription("one")).thenReturn(List.of(TEST_GYM_CLASS_1));
        when(gymClassRepository.findByTypeOrDescription("two")).thenReturn(List.of(TEST_GYM_CLASS_2));
        when(gymClassRepository.findByTypeOrDescription("one-two"))
                .thenReturn(List.of(TEST_GYM_CLASS_1, TEST_GYM_CLASS_2));
        when(gymClassRepository.findByTypeOrDescription("empty")).thenReturn(List.of());

        assertThat(findProductsService.findByTypeOrDescription("one")).containsExactly(TEST_GYM_CLASS_1);
        assertThat(findProductsService.findByTypeOrDescription("two")).containsExactly(TEST_GYM_CLASS_2);
        assertThat(findProductsService.findByTypeOrDescription("one-two"))
                .containsExactly(TEST_GYM_CLASS_1, TEST_GYM_CLASS_2);
        assertThat(findProductsService.findByTypeOrDescription("empty")).isEmpty();
    }

    @Test
    @DisplayName("given a too short search query")
    void test2() {
        String searchQuery = "x";
        ThrowableAssert.ThrowingCallable invocation =
                () -> findProductsService.findByTypeOrDescription(searchQuery);

        assertThatIllegalArgumentException().isThrownBy(invocation);
    }
}
