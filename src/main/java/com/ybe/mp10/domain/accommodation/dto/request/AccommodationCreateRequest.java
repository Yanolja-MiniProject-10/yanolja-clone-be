package com.ybe.mp10.domain.accommodation.dto.request;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.model.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "숙박 상품 생성 요청 데이터")
public class AccommodationCreateRequest {

    String name;
    String description;
    String address;
    String region;
    String category;


    public static Accommodation toEntity(AccommodationCreateRequest accommodationCreateRequest) {
        return Accommodation.builder()
                .name(accommodationCreateRequest.getName())
                .description(accommodationCreateRequest.getDescription())
                .address(accommodationCreateRequest.getAddress())
                .region(Region.fromValue(accommodationCreateRequest.getRegion()))
                .category(Category.fromValue(accommodationCreateRequest.getCategory()))
                .build();
    }
}
