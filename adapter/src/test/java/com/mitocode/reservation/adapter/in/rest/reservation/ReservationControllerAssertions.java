package com.mitocode.reservation.adapter.in.rest.reservation;

import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

import com.mitocode.reservation.model.reservation.Reservation;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public final class ReservationControllerAssertions {

    private ReservationControllerAssertions() {}

    public static void assertThatResponseIsReservation(Response response, Reservation reservation) {
        assertThat(response.statusCode()).isEqualTo(OK.getStatusCode());

        JsonPath json = response.jsonPath();

        assertThat(json.getString("classId.value")).isEqualTo(reservation.gymClass().id().value());
        assertThat(json.getString("classType")).isEqualTo(reservation.gymClass().type());
        assertThat(json.getString("classDescription")).isEqualTo(reservation.gymClass().description());

        assertThat(json.getString("customerId.email")).isEqualTo(reservation.customerId().email());
        assertThat(json.getInt("spotsReserved")).isEqualTo(reservation.spotsReserved());
        assertThat(json.getString("status")).isEqualTo(reservation.status().name());

    }

    public static void assertThatResponseContainsReservations(Response response, List<Reservation> expectedReservations) {
        assertThat(response.statusCode()).isEqualTo(OK.getStatusCode());

        JsonPath json = response.jsonPath();
        assertThat(json.getList("$").size()).isEqualTo(expectedReservations.size());

        for (int i = 0; i < expectedReservations.size(); i++) {
            Reservation expectedReservation = expectedReservations.get(i);

            assertThat(json.getString("[" + i + "].classId.value")).isEqualTo(expectedReservation.gymClass().id().value());
            assertThat(json.getString("[" + i + "].classType")).isEqualTo(expectedReservation.gymClass().type());
            assertThat(json.getString("[" + i + "].classDescription")).isEqualTo(expectedReservation.gymClass().description());

            assertThat(json.getString("[" + i + "].customerId.email")).isEqualTo(expectedReservation.customerId().email());
            assertThat(json.getInt("[" + i + "].spotsReserved")).isEqualTo(expectedReservation.spotsReserved());
            assertThat(json.getString("[" + i + "].status")).isEqualTo(expectedReservation.status().name());

        }
    }
}