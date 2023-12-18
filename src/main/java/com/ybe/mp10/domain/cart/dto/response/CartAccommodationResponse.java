package com.ybe.mp10.domain.cart.dto.response;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.model.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartAccommodationResponse {

    @Schema(example = "1")
    private Long accommodationId;

    @Schema(example = "해밀턴 호텔")
    private String name;

    @Schema(example = "서울특별시 네모구 동그라미동")
    private String address;

//    @Schema(example = "좋은 호텔!!")
//    private String description;
//
//    @Schema(example = "SEOUL")
//    private Region region;
//
//    @Schema(example = "HOTEL_RESORT")
//    private Category category;

    @Schema(example = "https://s3.us-west-1.amazonaws.com/myBucket/object%20key?query=%5Bbrackets%5D")
    private String thumbnailImageUrl;

    private List<CartRoomOptionResponse> roomOptions;


    public static CartAccommodationResponse toAccommodationEntity(Accommodation accommodation) {
        return CartAccommodationResponse.builder()
            .accommodationId(accommodation.getId())
            .name(accommodation.getName())
            .address(accommodation.getAddress())
//            .description(accommodation.getDescription())
//            .region(accommodation.getRegion())
//            .category(accommodation.getCategory())
            .thumbnailImageUrl(accommodation.getThumbnailImageUrl())
            .roomOptions(new ArrayList<>())
            .build();
    }
}
