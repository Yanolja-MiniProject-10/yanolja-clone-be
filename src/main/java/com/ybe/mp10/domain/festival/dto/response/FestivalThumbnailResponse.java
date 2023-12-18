package com.ybe.mp10.domain.festival.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "행사 정보 조회시 리턴 데이터")
public class FestivalThumbnailResponse {
    Long id;
    String title;
    String thumbnailImageUrl;
    LocalDate startDate;
    LocalDate endDate;
    String address;
    String city;
    String area;
    String phoneNumber;
}
