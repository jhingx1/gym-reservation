package com.mitocode.reservation.adapter.in.rest.common;

import com.mitocode.reservation.model.gymclass.ClassId;
import org.springframework.http.HttpStatus;

import static com.mitocode.reservation.adapter.in.rest.common.ControllerCommons.clientErrorException;

public class ClassIdParser {
    private ClassIdParser(){}

    public static ClassId parseClassId(String string) {
        if (string == null) {
            throw clientErrorException(HttpStatus.BAD_REQUEST, "Missing 'classId'");
        }

        try {
            return new ClassId(string);
        } catch (IllegalArgumentException e) {
            throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'classId'");
        }
    }

}