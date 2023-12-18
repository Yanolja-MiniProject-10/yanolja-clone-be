package com.ybe.mp10.domain.cart.dto.request;

import com.ybe.mp10.domain.cart.model.Cart;
import com.ybe.mp10.domain.cart.model.CartProduct;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.global.common.ReservationInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CartProductRequest {

    @Schema(description = "룸옵션ID", example = "4")
    @NotNull(message = "룸옵션ID는 필수 입니다.")
    private Long roomOptionId;

    @Schema(description = "숙박 인원 수", example = "2")
    private Long numberOfGuest;

    @Schema(description = "예약 시작일", example = "2023-11-12")
    private LocalDate reservationStartDate;

    @Schema(description = "예약 종료일", example = "2023-11-15")
    private LocalDate reservationEndDate;

    @Schema(description = "숙박 일시", example = "2")
    private Long stayDuration;


    public static CartProduct toEntity(CartProductRequest cartProductRequest, Cart cart) {
        return CartProduct.builder()
            .roomOption(RoomOption.builder()
                .id(cartProductRequest.getRoomOptionId())
                .build())
            .reservationInfo(ReservationInfo.builder()
                .numberOfGuest(cartProductRequest.getNumberOfGuest())
                .reservationStartTime(
                    cartProductRequest.getReservationStartDate().atStartOfDay())
                .reservationEndTime(
                    cartProductRequest.getReservationEndDate().atStartOfDay())
                .stayDuration(cartProductRequest.getStayDuration())
                .build()
            )
            .cart(cart)
            .build();
    }
}
