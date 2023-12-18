package com.ybe.mp10.domain.payment.dto.request;

import com.ybe.mp10.domain.payment.exception.NoSuchRoomOptionException;
import com.ybe.mp10.global.common.enums.Transportation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentRequest {

    @NotNull(message = "cartId는 필수 입니다.")
    @Schema(example = "1")
    private Long cartId;

    @NotNull(message = "cartProducts는 필수 입니다.")
    @NotEmpty(message = "cartPOrodcuts의 값은 필수 입니다.")
    private List<PaymentCartProduct> cartProducts;


}
