package com.ybe.mp10.global.common;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfo {
    private Long numberOfGuest;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private Long stayDuration;

}
