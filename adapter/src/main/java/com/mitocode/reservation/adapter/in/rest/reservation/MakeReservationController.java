package com.mitocode.reservation.adapter.in.rest.reservation;

import com.mitocode.reservation.application.port.in.reservation.MakeReservationUseCase;
import com.mitocode.reservation.application.port.in.reservation.GymClassNotFoundException;
import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.mitocode.reservation.adapter.in.rest.common.ClassIdParser.parseClassId;
import static com.mitocode.reservation.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static com.mitocode.reservation.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class MakeReservationController {

    private final MakeReservationUseCase makeReservationUseCase;

    @PreAuthorize("hasRole('ROLE_spring-keycloak-client_make-reservation')")
    @PostMapping("/{customerId}/reservation")
    public ReservationWebModel addLineItem(
            @PathVariable("customerId") String customerIdString,
            @RequestParam("classId") String classIdString,
            @RequestParam("quantity") int quantity){
        System.out.println("hola-make");
        CustomerId customerId = parseCustomerId(customerIdString);
        ClassId classId = parseClassId(classIdString);

        try{
            Reservation reservation = makeReservationUseCase.makeReservation(customerId, classId, quantity);
            return ReservationWebModel.fromDomainModel(reservation);
        }catch (GymClassNotFoundException e){
            throw clientErrorException(
                    HttpStatus.BAD_REQUEST, "The requested class does not exist");
        }catch (NotEnoughSpotsAvailableException e){
            throw clientErrorException(
                    HttpStatus.BAD_REQUEST, "Only %d spots available".formatted(e.availableSpots()));
        }
    }
}