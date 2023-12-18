package com.ybe.mp10.domain.region.controller;

import com.ybe.mp10.domain.region.dto.response.GetAllRegion;
import com.ybe.mp10.domain.region.service.RegionService;
import com.ybe.mp10.global.common.constant.ResponseConstant;
import com.ybe.mp10.global.response.GlobalDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;

@Tag(name = "지역 관련 API", description = "지역 관련 API 입니다")
@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;
    @Operation(summary = "전체 지역 조회 API", description = "전체 지역 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = GetAllRegion.class)))
    @GetMapping("")
    public ResponseEntity<?> getRegions() {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, regionService.getRegions()));
    }
}
