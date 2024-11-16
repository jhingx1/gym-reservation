package com.mitocode.reservation.model.gymclass;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public record ClassId(String value) {

    private static final String ALPHABET = "23456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH_OF_NEW_CLASS_IDS = 8;

    public ClassId {
        Objects.requireNonNull(value, "'value' must not be null");
        if (value.isEmpty()) {
            throw new IllegalArgumentException("'value' must not be empty");
        }
    }

    public static ClassId randomClassId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        char[] chars = new char[LENGTH_OF_NEW_CLASS_IDS];
        for (int i = 0; i < LENGTH_OF_NEW_CLASS_IDS; i++) {
            chars[i] = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
        }
        return new ClassId(new String(chars));
    }
}
