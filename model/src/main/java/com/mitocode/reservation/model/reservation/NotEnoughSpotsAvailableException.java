package com.mitocode.reservation.model.reservation;

public class NotEnoughSpotsAvailableException extends Exception {
    private final int availableSpots;

    public NotEnoughSpotsAvailableException(String message, int availableSpots) {
        super(message);
        this.availableSpots = availableSpots;
    }

    public int availableSpots() {
        return availableSpots;
    }
}
