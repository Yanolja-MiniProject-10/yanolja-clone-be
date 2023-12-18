package com.ybe.mp10.domain.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InstantPaymentRequest {

    @NotNull(message = "roomOption ID는 필수 입니다.")
    @Schema(example = "1")
    private Long roomOptionId;

    @NotNull(message = "reservationStartDate는 필수 입니다.")
    @Schema(example = "2023-12-25")
    private LocalDate reservationStartDate;

    @NotNull(message = "reservationEndDate는 필수 입니다." )
    @Schema(example = "2023-12-27")
    private LocalDate reservationEndDate;

    @NotNull(message = "stayDuration은 필수 입니다.")
    @Schema(example = "2")
    private Long stayDuration;

    @Schema(example = "3")
    private Long numberOfGuest;

}
