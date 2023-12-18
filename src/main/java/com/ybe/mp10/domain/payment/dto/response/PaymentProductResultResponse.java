package com.ybe.mp10.domain.payment.dto.response;

import com.ybe.mp10.domain.payment.model.PaymentProduct;
import com.ybe.mp10.global.common.enums.Transportation;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentProductResultResponse {

    private Long paymentProductId;

    private Long accommodationId;

    private String accommodationName;

    private Long roomOptionId;

    private String roomOptionName;

    private String description;

    private Long price;

    private Long numberOfGuest;

    private LocalDate reservationStartTime;

    private LocalDate reservationEndTime;

    private Long stayDuration;

    private String thumbnailImageUrl;

    private Transportation transportation;

    public static PaymentProductResultResponse fromEntity(PaymentProduct paymentProduct){
        return PaymentProductResultResponse.builder()
            .paymentProductId(paymentProduct.getId())
            .accommodationId(paymentProduct.getAccommodationId())
            .accommodationName(paymentProduct.getAccommodationName())
            .roomOptionId(paymentProduct.getRoomOptionId())
            .roomOptionName(paymentProduct.getRoomOptionName())
            .description(paymentProduct.getDescription())
            .price(paymentProduct.getPaymentAmount())
            .reservationStartTime(paymentProduct.getReservationInfo()
                .getReservationStartTime().toLocalDate())
            .reservationEndTime(paymentProduct.getReservationInfo()
                .getReservationEndTime().toLocalDate())
            .stayDuration(paymentProduct.getReservationInfo()
                .getStayDuration())
            .thumbnailImageUrl(paymentProduct.getThumbnailImageUrl())
            .transportation(paymentProduct.getTransportation())
            .build();


    }


}
