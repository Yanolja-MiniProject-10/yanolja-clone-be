package com.ybe.mp10.domain.festival.controller;

import com.ybe.mp10.domain.festival.dto.response.FestivalThumbnailResponse;
import com.ybe.mp10.domain.festival.service.FestivalService;
import com.ybe.mp10.global.response.GlobalDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "행사 관련 API", description = "행사 관련 API 입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/festival")
public class FestivalController {
    private final FestivalService festivalService;

    @Operation(summary = "행사 정보 조회 API", description = "행사 정보 조회 API 입니다")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = FestivalThumbnailResponse.class)))
    @GetMapping("")
    public ResponseEntity<?> getFestival(
            @Parameter(name = "eventStartDate", description = "행사 시작일", required = true, in = QUERY)
            @RequestParam("eventStartDate") LocalDate eventStartDate, Pageable pageable) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, festivalService.getFestival(eventStartDate, pageable)));
    }
}
