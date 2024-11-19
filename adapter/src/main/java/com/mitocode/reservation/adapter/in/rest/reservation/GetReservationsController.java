package com.mitocode.reservation.adapter.in.rest.reservation;

import com.mitocode.reservation.application.port.in.reservation.GetUserReservationsUseCase;
import com.mitocode.reservation.model.customer.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mitocode.reservation.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class GetReservationsController {

    private final GetUserReservationsUseCase getUserReservationsUseCase;

    @GetMapping("/{customerId}")
    public List<ReservationWebModel> getUserReservations(@PathVariable("customerId") String customerIdString) {
        CustomerId customerId = parseCustomerId(customerIdString);
        return getUserReservationsUseCase.getReservations(customerId)
                .stream()
                .map(ReservationWebModel::fromDomainModel)
                .toList();
    }
}