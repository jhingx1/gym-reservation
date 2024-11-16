package com.mitocode.reservation.adapter.in.rest.reservation;

import com.mitocode.reservation.application.port.in.reservation.MakeReservationUseCase;
import com.mitocode.reservation.application.port.in.reservation.GymClassNotFoundException;
import com.mitocode.reservation.model.reservation.NotEnoughSpotsAvailableException;
import com.mitocode.reservation.model.customer.CustomerId;
import com.mitocode.reservation.model.gymclass.ClassId;
import com.mitocode.reservation.model.reservation.Reservation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import static com.mitocode.reservation.adapter.in.rest.common.ClassIdParser.parseClassId;
import static com.mitocode.reservation.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static com.mitocode.reservation.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class MakeReservationController {

    private final MakeReservationUseCase makeReservationUseCase;

    @POST
    @Path("/{customerId}/reservation")
    public ReservationWebModel addLineItem(
            @PathParam("customerId") String customerIdString,
            @QueryParam("classId") String classIdString,
            @QueryParam("quantity") int quantity){
        CustomerId customerId = parseCustomerId(customerIdString);
        ClassId classId = parseClassId(classIdString);

        try{
            Reservation reservation = makeReservationUseCase.makeReservation(customerId, classId, quantity);
            return ReservationWebModel.fromDomainModel(reservation);
        }catch (GymClassNotFoundException e){
            throw clientErrorException(
                    Response.Status.BAD_REQUEST, "The requested class does not exist");
        }catch (NotEnoughSpotsAvailableException e){
            throw clientErrorException(
                    Response.Status.BAD_REQUEST, "Only %d spots available".formatted(e.availableSpots()));
        }
    }
}