package com.ybe.mp10.domain.accommodation.dto.response;

import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.model.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
@Schema(description = "전체 숙박 상품 조회시 리턴 데이터")
public class AccommodationResponse {
    Long id;
    String name;
    String thumbnailImageUrl;
    String category;
    String region;
    Long minimumPrice;
    Long maximumPrice;
    @Builder
    public AccommodationResponse(Long id, String name, String thumbnailImageUrl, Category category, Region region, Long minimumPrice, Long maximumPrice) {
        this.id = id;
        this.name = name;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.category = category.getValue();
        this.region = region.getValue();
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
    }

    public AccommodationResponse(Long id, String name, String thumbnailImageUrl, Category category, Region region, Long minimumPrice, Long maximumPrice, Long additionalPrice) {
        this.id = id;
        this.name = name;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.category = category.getValue();
        this.region = region.getValue();
        this.minimumPrice = minimumPrice + additionalPrice;
        this.maximumPrice = maximumPrice + additionalPrice;
    }
}
