package com.mitocode.reservation.model.customer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CustomerIdTest {

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "short"})
    void givenInvalidEmail_newCustomerId_throwsException(String email) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new CustomerId(email))
                .withMessageContaining("Invalid email address");
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid@example.com", "another.valid123@domain.com"})
    void givenValidEmail_newCustomerId_succeeds(String email) {
        CustomerId customerId = new CustomerId(email);
        assertThat(customerId.email()).isEqualTo(email);
    }

}
