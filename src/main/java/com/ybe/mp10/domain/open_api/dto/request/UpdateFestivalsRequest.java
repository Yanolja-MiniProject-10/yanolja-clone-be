package com.ybe.mp10.domain.open_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "행사 업데이트 요청 데이터")
public class UpdateFestivalsRequest {
    @NotNull
    String areaCode;
    @NotNull
    String sigunguCode;
    @NotNull
    String eventStartDate;
    @NotNull
    String row;
    @NotNull
    String area;
    @NotNull
    String sigungu;
}
