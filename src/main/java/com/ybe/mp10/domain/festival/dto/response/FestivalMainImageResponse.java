package com.ybe.mp10.domain.festival.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "행사 정보 상세 조회시 리턴 데이터")
public class FestivalMainImageResponse {
    private Long id;
    private String title;
    private String phoneNumber;
    private String mainImageUrl;
    private String thumbnailImageUrl;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private String address;
    private String area;
    private String city;
}
