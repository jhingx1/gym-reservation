package com.mitocode.reservation.adapter.in.rest.common;

import com.mitocode.reservation.model.customer.CustomerId;
import jakarta.ws.rs.core.Response;

import static com.mitocode.reservation.adapter.in.rest.common.ControllerCommons.clientErrorException;

public class CustomerIdParser {
    private CustomerIdParser(){}

    public static CustomerId parseCustomerId(String string) {
        if (string == null || string.isBlank()) {
            throw clientErrorException(Response.Status.BAD_REQUEST, "Missing 'email'");
        }

        try {
            return new CustomerId(string);
        } catch (IllegalArgumentException e) {
            throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'email'");
        }
    }
}
