package com.mitocode.reservation.adapter.out.persistence.jpa;


import com.mitocode.reservation.model.reservation.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Reservation")
@Getter
@Setter
public class ReservationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_class_id", nullable = false)
    private GymClassJpaEntity gymClass;

    @Column(nullable = false)
    private int spotsReserved;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

}
