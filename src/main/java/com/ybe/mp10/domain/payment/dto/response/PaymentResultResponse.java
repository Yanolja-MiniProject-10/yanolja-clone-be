package com.ybe.mp10.domain.payment.dto.response;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.payment.model.Payment;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResultResponse {

    private Long paymentId;

    //TODO : 가격을 유동적으로 보내야 한다. TotalPrice를 보냄
    private Long paymentAmount;

    private Boolean paymentCanceled;

    private String reservationNumber;

    private List<PaymentResultAccommodationResponse> accommodations;

    public static PaymentResultResponse fromEntity(
        Payment payment,
        List<PaymentResultAccommodationResponse> accommodations) {
        return PaymentResultResponse.builder()
            .paymentId(payment.getId())
            .paymentAmount(payment.getPaymentAmount())
            .paymentCanceled(payment.getPaymentCanceled())
            .reservationNumber(payment.getReservationNumber())
            .accommodations(accommodations)
            .build();
    }

    public PaymentResultAccommodationResponse findAccommodation(Long id){
        for(PaymentResultAccommodationResponse r : accommodations){
            if(r.getAccommodationId().equals(id)){
                return r;
            }
        }
        return null;
    }

}
