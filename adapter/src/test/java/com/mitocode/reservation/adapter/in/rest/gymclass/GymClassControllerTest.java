package com.mitocode.reservation.adapter.in.rest.gymclass;

import static com.mitocode.reservation.adapter.in.rest.HttpTestCommons.TEST_PORT;
import static com.mitocode.reservation.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static com.mitocode.reservation.adapter.in.rest.gymclass.GymClassControllerAssertions.assertThatResponseIsGymClassList;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.mitocode.reservation.application.port.in.gymclass.FindGymClassUseCase;
import com.mitocode.reservation.model.gymclass.GymClass;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Application;
import java.util.List;
import java.util.Set;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GymClassControllerTest {

    private static final GymClass TEST_GYM_CLASS_1 = createTestClass(30, 20);
    private static final GymClass TEST_GYM_CLASS_2 = createTestClass(25, 10);

    private static final FindGymClassUseCase findGymClassUseCase = mock(FindGymClassUseCase.class);

    private static UndertowJaxrsServer server;

    @BeforeAll
    static void init() {
        server = new UndertowJaxrsServer()
                .setPort(TEST_PORT)
                .start()
                .deploy(new Application() {
                    @Override
                    public Set<Object> getSingletons() {
                        return Set.of(new FindGymClassesController(findGymClassUseCase));
                    }
                });
    }

    @AfterAll
    static void stop() {
        server.stop();
    }

    @BeforeEach
    void resetMocks() {
        Mockito.reset(findGymClassUseCase);
    }

    @Test
    void givenAQueryAndAListOfGymClasses_findGymClasses_requestsGymClassesViaQueryAndReturnsThem() {
        String query = "Yoga";
        List<GymClass> gymClassList = List.of(TEST_GYM_CLASS_1, TEST_GYM_CLASS_2);

        when(findGymClassUseCase.findByTypeOrDescription(query)).thenReturn(gymClassList);

        Response response = given()
                .port(TEST_PORT)
                .queryParam("query", query)
                .get("/gym-classes")
                .then()
                .extract()
                .response();

        assertThatResponseIsGymClassList(response, gymClassList);
    }

    @Test
    void givenANullQuery_findGymClasses_returnsError() {
        Response response = given()
                .port(TEST_PORT)
                .get("/gym-classes")
                .then()
                .extract()
                .response();

        assertThatResponseIsError(response, BAD_REQUEST, "Missing 'query'");
    }

    @Test
    void givenATooShortQuery_findGymClasses_returnsError() {
        String query = "e";
        when(findGymClassUseCase.findByTypeOrDescription(query))
                .thenThrow(IllegalArgumentException.class);

        Response response = given()
                .port(TEST_PORT)
                .queryParam("query", query)
                .get("/gym-classes")
                .then()
                .extract()
                .response();

        assertThatResponseIsError(response, BAD_REQUEST, "Invalid 'query'");
    }
}