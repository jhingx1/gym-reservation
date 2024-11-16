package com.mitocode.reservation.adapter.in.rest.gymclass;

import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

import com.mitocode.reservation.model.gymclass.GymClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.List;

public final class GymClassControllerAssertions {

    private GymClassControllerAssertions() {}

    public static void assertThatResponseIsGymClass(Response response, GymClass aGymClass) {
        assertThat(response.statusCode()).isEqualTo(OK.getStatusCode());

        JsonPath json = response.jsonPath();

        assertThatJsonGymClassMatchesGymClass(json, "", aGymClass);
    }

    public static void assertThatResponseIsGymClassList(Response response, List<GymClass> gymClasses) {
        assertThat(response.statusCode()).isEqualTo(OK.getStatusCode());

        JsonPath json = response.jsonPath();

        for (int i = 0; i < gymClasses.size(); i++) {
            String prefix = "[%d].".formatted(i);
            GymClass aGymClass = gymClasses.get(i);
            assertThatJsonGymClassMatchesGymClass(json, prefix, aGymClass);
        }
    }

    static void assertThatJsonGymClassMatchesGymClass(
            JsonPath json, String prefix, GymClass aGymClass) {

        assertThat(json.getString(prefix + "id")).isEqualTo(aGymClass.id().value());
        assertThat(json.getString(prefix + "type")).isEqualTo(aGymClass.type());
        assertThat(json.getString(prefix + "description")).isEqualTo(aGymClass.description());
        assertThat(json.getInt(prefix + "capacity")).isEqualTo(aGymClass.capacity());
        assertThat(json.getInt(prefix + "spotsAvailable")).isEqualTo(aGymClass.spotsAvailable());

    }
}