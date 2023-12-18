package com.ybe.mp10.domain.region.dto.response;

import com.ybe.mp10.domain.region.model.Region;
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
@Schema(description = "전체 지역 조회시 리턴 데이터")
public class GetAllRegion {
    List<String> regions;
}
