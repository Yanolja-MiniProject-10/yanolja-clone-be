package com.ybe.mp10.domain.payment.dto.response;

import com.ybe.mp10.domain.payment.exception.NoSuchAccommodationException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {


    private Long cartId;

    private Long count;

    private List<PaymentAccommodationResponse> accommodations;


}
