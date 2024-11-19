package com.mitocode.reservation;

import com.mitocode.reservation.application.port.in.gymclass.FindGymClassUseCase;
import com.mitocode.reservation.application.port.in.reservation.CancelReservationUseCase;
import com.mitocode.reservation.application.port.in.reservation.GetUserReservationsUseCase;
import com.mitocode.reservation.application.port.in.reservation.MakeReservationUseCase;
import com.mitocode.reservation.application.port.out.persistence.GymClassRepository;
import com.mitocode.reservation.application.port.out.persistence.ReservationRepository;
import com.mitocode.reservation.application.service.gymclass.FindGymClassService;
import com.mitocode.reservation.application.service.reservation.CancelReservationService;
import com.mitocode.reservation.application.service.reservation.GetUserReservationsService;
import com.mitocode.reservation.application.service.reservation.MakeReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAppConfig {

    @Autowired
    GymClassRepository gymClassRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Bean
    GetUserReservationsUseCase getUserReservationsUseCase(){
        return new GetUserReservationsService(reservationRepository);
    }

    @Bean
    CancelReservationUseCase cancelReservationUseCase(){
        return new CancelReservationService(reservationRepository, gymClassRepository);
    }

    @Bean
    MakeReservationUseCase makeReservationUseCase(){
        return new MakeReservationService(reservationRepository, gymClassRepository);
    }

    @Bean
    FindGymClassUseCase findGymClassUseCase(){
        return new FindGymClassService(gymClassRepository);
    }
}