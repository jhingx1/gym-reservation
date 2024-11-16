package com.mitocode.reservation.adapter.in.rest.reservation;

import com.mitocode.reservation.application.port.in.reservation.GetUserReservationsUseCase;
import com.mitocode.reservation.model.customer.CustomerId;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

import static com.mitocode.reservation.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class GetReservationsController {

    private final GetUserReservationsUseCase getUserReservationsUseCase;

    @GET
    @Path("/{customerId}")
    public List<ReservationWebModel> getUserReservations(@PathParam("customerId") String customerIdString) {
        CustomerId customerId = parseCustomerId(customerIdString);
        return getUserReservationsUseCase.getReservations(customerId)
                .stream()
                .map(ReservationWebModel::fromDomainModel)
                .toList();
    }
}