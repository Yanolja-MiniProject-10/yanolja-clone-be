package com.ybe.mp10.domain.payment.dto.response;

import com.ybe.mp10.domain.cart.model.CartProduct;
import com.ybe.mp10.global.common.enums.Transportation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRoomOptionResponse {

    @Schema(example = "1")
    private Long cartProductId;

    @Schema(example = "3")
    private Long roomOptionId;

    @Schema(example = "2인실(오션뷰)")
    private String name;
    @Schema(example = "https://s3.us-west-1.amazonaws.com/myBucket/object%20key?query=%5Bbrackets%5D")
    private String thumbnailImage;

//    @Schema(example = "10")
//    private Long totalRoomCount;

    //private Long reservedRoomCount;
    //TODO : 룸카운트와 예약된 룸카운트를 같이 보낸다. 같으면 예매 완료

    @Schema(example = "2")
    private Long capacity;

    @Schema(example = "20000")
    private Long pricePerNight;

    //TODO : 가격을 유동적으로 보내야 한다. TotalPrice를 보냄
    private Long totalPrice;

    @Schema(example = "2020-12-12")
    private LocalDate reservationStartDate;

    @Schema(example = "2020-12-15")
    private LocalDate reservationEndDate;

    @Schema(example = "3")
    private Long stayDuration;

    @Schema(example = "도보")
    private Transportation transportation;

    public static PaymentRoomOptionResponse fromEntity(CartProduct cartProduct) {
        return PaymentRoomOptionResponse.builder()
            .cartProductId(cartProduct.getId())
            .roomOptionId(cartProduct.getRoomOption().getId())
            .name(cartProduct.getRoomOption().getName())
            .thumbnailImage(cartProduct.getRoomOption().getThumbnailImage())
            .capacity(cartProduct.getRoomOption().getCapacity())
            .pricePerNight(cartProduct.getRoomOption().getDefaultPrice())
            .totalPrice(cartProduct.getRoomOption().getDefaultPrice() * cartProduct.getReservationInfo().getStayDuration())
            .reservationStartDate(cartProduct.getReservationInfo()
                .getReservationStartTime()
                .toLocalDate())
            .reservationEndDate(cartProduct.getReservationInfo()
                .getReservationEndTime()
                .toLocalDate()
            )
            .stayDuration(cartProduct.getReservationInfo().getStayDuration())
            .transportation(cartProduct.getTransportation() == null ? Transportation.CAR : cartProduct.getTransportation())
            .build();
    }
}
