package com.mitocode.reservation.model.customer;

public record CustomerId(String email) {

    public CustomerId {
        if (!email.contains("@") || email.length() < 5) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
