package com.ybe.mp10.domain.payment.dto.response;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentResultAccommodationResponse {

    private Long accommodationId;

    private String name;

    @Schema(example = "서울특별시 네모구 동그라미동")
    private String address;

    @Schema(example = "https://s3.us-west-1.amazonaws.com/myBucket/object%20key?query=%5Bbrackets%5D")
    private String thumbnailImageUrl;

    private List<PaymentResultRoomOptionResponse> roomOptions;

    public static PaymentResultAccommodationResponse fromEntity(Accommodation accommodation){
        return PaymentResultAccommodationResponse.builder()
            .accommodationId(accommodation.getId())
            .name(accommodation.getName())
            .address(accommodation.getAddress())
            .thumbnailImageUrl(accommodation.getThumbnailImageUrl())
            .roomOptions(new ArrayList<>())
            .build();
    }


}
