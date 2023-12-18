package com.ybe.mp10.domain.payment.dto.request;

import com.ybe.mp10.global.common.enums.Transportation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentCartProduct {

    @NotNull(message = "cartProductId는 필수 입니다.")
    @Schema(example = "1")
    private Long cartProductId;

    @Schema(example = "도보")
    private String transportation;

}
