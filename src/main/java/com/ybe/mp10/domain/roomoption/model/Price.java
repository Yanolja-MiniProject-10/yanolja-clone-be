package com.ybe.mp10.domain.roomoption.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "price_id")
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "room_option_id")
    private RoomOption roomOption;

}
