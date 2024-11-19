package com.mitocode.reservation.adapter.in.rest.gymclass;

import com.mitocode.reservation.application.port.in.gymclass.FindGymClassUseCase;
import com.mitocode.reservation.model.gymclass.GymClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mitocode.reservation.adapter.in.rest.common.ControllerCommons.clientErrorException;

@RestController
@RequestMapping("/gym-classes")
@RequiredArgsConstructor
public class FindGymClassesController {

    private final FindGymClassUseCase findGymClassUseCase;

    @GetMapping
    public List<GymClassInListWebModel> findGymClasses(
            @RequestParam(value = "query", required = false) String query) {
        if (query == null) {
            throw clientErrorException(HttpStatus.BAD_REQUEST, "Missing 'query'");
        }

        List<GymClass> gymClasses;
        try {
            gymClasses = findGymClassUseCase.findByTypeOrDescription(query);
        } catch (IllegalArgumentException e) {
            throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'query'");
        }

        return gymClasses.stream().map(GymClassInListWebModel::fromDomainModel).toList();

    }
}