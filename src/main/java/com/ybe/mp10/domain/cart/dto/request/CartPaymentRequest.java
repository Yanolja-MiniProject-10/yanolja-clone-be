package com.ybe.mp10.domain.cart.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartPaymentRequest {

    @NotNull(message = "cartId는 필수 입니다.")
    @Schema(example = "1")
    private Long cartId;

    @NotEmpty(message = "장바구니 상품 목록은 필수 입니다.")
    @Schema(example = "[1,2,3]")
    private List<Long> cartProducts;

}
