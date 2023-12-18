package com.ybe.mp10.domain.open_api.controller;

import com.ybe.mp10.domain.open_api.dto.request.UpdateFestivalsRequest;
import com.ybe.mp10.domain.open_api.dto.response.AreaCodeResponse;
import com.ybe.mp10.domain.open_api.dto.response.SigunguCodeResponse;
import com.ybe.mp10.domain.open_api.dto.response.UpdateFestivalsResponse;
import com.ybe.mp10.domain.open_api.service.OpenApiService;
import com.ybe.mp10.global.response.GlobalDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "오픈 API 관련 API", description = "오픈 API 관련 API 입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api")
public class OpenApiController {
    private final OpenApiService openApiService;

    @Operation(summary = "오픈 API 지역 코드 조회 API", description = "지역 코드 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = AreaCodeResponse.class)))
    @GetMapping("/area-code")
    public ResponseEntity<?> getAreaCode() {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, openApiService.getAreaCode()));
    }

    @Operation(summary = "오픈 API 시군구 코드 조회 API", description = "시군구 코드 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = SigunguCodeResponse.class)))
    @GetMapping("/sigungu-code")
    public ResponseEntity<?> getSigunguCode(
            @Parameter(name = "areaCode", description = "지역 코드", required = true, in = QUERY)
            @RequestParam("areaCode") String areaCode) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, openApiService.getSigunguCode(areaCode)));
    }

    @Operation(summary = "오픈 API 행사 정보 업데이트 API", description = "행사 정보 업데이트 API 입니다.")
    @ApiResponse(responseCode = "200", description = "업데이트 성공시", content = @Content(schema = @Schema(implementation = UpdateFestivalsResponse.class)))
    @PutMapping("/festival-info")
    public ResponseEntity<?> updateFestivalInfo(@Valid @RequestBody UpdateFestivalsRequest updateFestivalsRequest) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, openApiService.updateFestivalInfo(updateFestivalsRequest)));
    }
}
