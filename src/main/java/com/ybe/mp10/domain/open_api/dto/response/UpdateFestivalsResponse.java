package com.ybe.mp10.domain.open_api.dto.response;

import com.ybe.mp10.domain.festival.model.Festival;
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
@Schema(description = "행사 정보 업데이트시 리턴 데이터")
public class UpdateFestivalsResponse {
    List<Festival> updatedFestivals;
    Long totalUpdated;
}
