package com.mitocode.reservation.adapter.in.rest.common;

public record ErrorEntity(int httpStatus, String errorMessage) {
}
