package com.mitocode.reservation.adapter.in.rest.gymclass;

import static com.mitocode.reservation.adapter.in.rest.HttpTestCommons.assertThatResponseIsError;
import static com.mitocode.reservation.adapter.in.rest.gymclass.GymClassControllerAssertions.assertThatResponseIsGymClassList;
import static com.mitocode.reservation.model.gymclass.TestGymClassFactory.createTestClass;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.mitocode.reservation.application.port.in.gymclass.FindGymClassUseCase;
import com.mitocode.reservation.model.gymclass.GymClass;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GymClassControllerTest {

    private static final GymClass TEST_GYM_CLASS_1 = createTestClass(30, 20);
    private static final GymClass TEST_GYM_CLASS_2 = createTestClass(25, 10);

    @LocalServerPort
    private Integer TEST_PORT;

    @MockBean
    FindGymClassUseCase findGymClassUseCase;

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