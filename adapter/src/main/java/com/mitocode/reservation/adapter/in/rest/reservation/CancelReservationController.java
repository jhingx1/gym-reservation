package com.mitocode.reservation.adapter.in.rest.reservation;

import com.mitocode.reservation.application.port.in.reservation.CancelReservationUseCase;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.ReservationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mitocode.reservation.adapter.in.rest.common.ClassIdParser.parseClassId;
import static com.mitocode.reservation.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static com.mitocode.reservation.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class CancelReservationController {
    private final CancelReservationUseCase cancelReservationUseCase;
    //Este delete solo borrar una reserva
    @PreAuthorize("hasRole('ROLE_spring-keycloak-client_cancel-reservation')")
    @DeleteMapping("/{customerId}/class/{classId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("customerId") String customerIdString,
                                                  @PathVariable("classId") String classIdString) {
        CustomerId customerId = parseCustomerId(customerIdString);
        ClassId classId = parseClassId(classIdString);
        try{
            cancelReservationUseCase.cancelReservation(customerId, classId);
            return ResponseEntity.noContent().build();
        }catch (ReservationNotFoundException e){
            throw clientErrorException(
                    HttpStatus.BAD_REQUEST, "Reservation not found");
        }

    }
}