package com.ybe.mp10.domain.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DeletePaymentRequest {

    @NotNull(message = "paymentId는 필수 입니다.")
    @Schema(example = "12")
    private Long paymentId;

}
