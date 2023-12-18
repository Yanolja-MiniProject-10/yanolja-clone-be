package com.ybe.mp10.domain.accommodation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "숙박 상품 업데이트 요청 데이터")
public class AccommodationUpdateRequest {
    String name;
    String description;
    String address;
    String region;
    String category;
}
