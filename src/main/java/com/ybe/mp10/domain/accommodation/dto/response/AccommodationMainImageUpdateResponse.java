package com.ybe.mp10.domain.accommodation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "숙박 상품 메인 이미지들 업데이트시 리턴 데이터")
public class AccommodationMainImageUpdateResponse {
    List<String> mainImageUrls;
}
