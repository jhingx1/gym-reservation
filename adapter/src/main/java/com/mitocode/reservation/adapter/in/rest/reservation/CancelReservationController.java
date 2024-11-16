package com.mitocode.reservation.adapter.in.rest.reservation;

import com.mitocode.reservation.application.port.in.reservation.CancelReservationUseCase;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.ReservationNotFoundException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import static com.mitocode.reservation.adapter.in.rest.common.ClassIdParser.parseClassId;
import static com.mitocode.reservation.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static com.mitocode.reservation.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CancelReservationController {
    private final CancelReservationUseCase cancelReservationUseCase;

    @DELETE
    @Path("/{customerId}/class/{classId}")
    public void cancelReservation(@PathParam("customerId") String customerIdString,
                                      @PathParam("classId") String classIdString) {
        CustomerId customerId = parseCustomerId(customerIdString);
        ClassId classId = parseClassId(classIdString);
        try{
            cancelReservationUseCase.cancelReservation(customerId, classId);
        }catch (ReservationNotFoundException e){
            throw clientErrorException(
                    Response.Status.BAD_REQUEST, "Reservation not found");
        }

    }
}
