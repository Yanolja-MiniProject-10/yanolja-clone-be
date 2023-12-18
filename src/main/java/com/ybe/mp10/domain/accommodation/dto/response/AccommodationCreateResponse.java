package com.ybe.mp10.domain.accommodation.dto.response;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "숙박 상품 생성 리턴 데이터")
public class AccommodationCreateResponse {

    Long id;
    String name;
    String description;
    String address;
    String region;
    String category;

    public static AccommodationCreateResponse fromEntity(Accommodation accommodation) {
        return AccommodationCreateResponse.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .description(accommodation.getDescription())
                .address(accommodation.getAddress())
                .region(accommodation.getRegion().getValue())
                .category(accommodation.getCategory().getValue())
                .build();
    }
}
