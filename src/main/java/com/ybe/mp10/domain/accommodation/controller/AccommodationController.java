package com.ybe.mp10.domain.accommodation.controller;

import com.ybe.mp10.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.ybe.mp10.domain.accommodation.dto.request.RoomOptionRequest;
import com.ybe.mp10.domain.accommodation.dto.response.*;
import com.ybe.mp10.domain.accommodation.service.AccommodationService;
import com.ybe.mp10.domain.roomoption.dto.response.RoomOptionResponse;
import com.ybe.mp10.global.common.constant.ResponseConstant;
import com.ybe.mp10.global.response.GlobalDataResponse;
import com.ybe.mp10.global.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.ybe.mp10.global.common.constant.ResponseConstant.*;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "숙박 관련 API", description = "숙박 관련 API 입니다")
@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;
    @Operation(summary = "숙박 상품에 룸 옵션 추가", description = "개발용 API")
    @PostMapping("/roomoptions")
    public ResponseEntity<?> addRoomOptions(@RequestBody RoomOptionRequest roomOptionRequest) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, accommodationService.addRoomOptions(roomOptionRequest)));
    }
    @Operation(summary = "숙박 상품 룸 옵션 썸네일 변경", description = "개발용 API")
    @PatchMapping("/roomoptions/{roomOptionId}/thumbnail")
    public ResponseEntity<?> updateRoomOptionThumbnail(@PathVariable("roomOptionId") Long roomOptionId, @RequestParam("thumbnail") MultipartFile mainThumbnail) {
        return ResponseEntity.ok(GlobalDataResponse.ok(UPDATED, accommodationService.updateRoomOptionThumbnailImage(roomOptionId, mainThumbnail)));
    }
    @Operation(summary = "숙박 상품에 룸 옵션 메인 이미지 변경", description = "개발용 API")
    @PatchMapping("/roomoptions/{roomOptionId}/mainImages")
    public ResponseEntity<?> updateRoomOptionMainImages(@PathVariable("roomOptionId") Long roomOptionId, @RequestParam("mainImages") List<MultipartFile> mainImages) {
        return ResponseEntity.ok(GlobalDataResponse.ok(UPDATED, accommodationService.updateRoomOptionMainImages(roomOptionId, mainImages)));
    }


    @Operation(summary = "숙박 상품 생성 API", description = "숙박 상품 생성 API 입니다.")
    @ApiResponse(responseCode = "200", description = "생성 성공시", content = @Content(schema = @Schema(implementation = AccommodationCreateResponse.class)))
    @PostMapping("")
    public ResponseEntity<?> createAccommodation(@RequestBody AccommodationCreateRequest accommodationCreateRequest) {
        return ResponseEntity.ok(GlobalDataResponse.created(CREATED, accommodationService.createAccommodation(accommodationCreateRequest)));
    }


    @Operation(summary = "숙박 상품 썸네일 이미지 업데이트 API", description = "숙박 상품 메인 이미지 업데이트 API 입니다.")
    @ApiResponse(responseCode = "200", description = "썸네일 업데이트 성공시", content = @Content(schema = @Schema(implementation = AccommodationThumbnailUpdateResponse.class)))
    @PatchMapping("/{accommodationId}/thumbnail")
    public ResponseEntity<?> updateAccommodationThumbnail(@PathVariable("accommodationId") Long accommodationId, @RequestParam("thumbnail") MultipartFile mainThumbnail) {
        return ResponseEntity.ok(GlobalDataResponse.ok(UPDATED, accommodationService.updateThumbnailImage(accommodationId, mainThumbnail)));
    }

    @Operation(summary = "숙박 상품 메인 이미지 업데이트 API", description = "숙박 상품 메인 이미지 업데이트 API 입니다.")
    @ApiResponse(responseCode = "200", description = "메인 이미지 업데이트 성공시", content = @Content(schema = @Schema(implementation = AccommodationMainImageUpdateResponse.class)))
    @PatchMapping("/{accommodationId}/mainImages")
    public ResponseEntity<?> updateAccommodationMainImages(@PathVariable("accommodationId") Long accommodationId, @RequestParam("mainImages") List<MultipartFile> mainImages) {
        return ResponseEntity.ok(GlobalDataResponse.ok(UPDATED, accommodationService.updateMainImages(accommodationId, mainImages)));
    }

    @Operation(summary = "숙박 상품 삭제 API", description = "숙박 상품 삭제 API 입니다.")
    @DeleteMapping("/{accommodationId}")
    public ResponseEntity<?> deleteAccommodation(@PathVariable("accommodationId") Long accommodationId) {
        accommodationService.deleteAccommodation(accommodationId);
        return ResponseEntity.ok(GlobalResponse.ok(DELETED));
    }

    @Operation(summary = "전체 숙박 상품 조회 API", description = "전체 숙박 상품 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = AccommodationResponse.class)))
    @GetMapping("")
    public ResponseEntity<?> getAccommodations(Pageable pageable,
                                               @Parameter(name = "startDate", description = "숙박 시작 날짜, 2023-11-21", in = QUERY, required = true)
                                               @RequestParam(value = "startDate", required = true)
                                               LocalDate startDate,
                                               @Parameter(name = "endDate", description = "숙박 종료 날짜, 2023-11-22", in = QUERY, required = true)
                                               @RequestParam(value = "endDate", required = true)
                                               LocalDate endDate,
                                               @Parameter(name = "guest", description = "숙박 인원 수", in = QUERY, required = true)
                                               @RequestParam(value = "guest", required = true)
                                               Long guest,
                                               @Parameter(name = "name", description = "검색할 숙소 이름", in = QUERY)
                                               @RequestParam(value = "name", required = false)
                                               String name,
                                               @Parameter(name = "region", description = "검색할 지역 ex) 서울, 강원, 경기, 제주", in = QUERY)
                                               @RequestParam(value = "region", required = false)
                                               String region) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, accommodationService.getAccommodations(startDate, endDate, guest, name, region, pageable)));
    }

    @Operation(summary = "최근 본 상품과 연관 상품 조회 API", description = "연관 상품 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = AccommodationResponse.class)))
    @GetMapping("/related")
    public ResponseEntity<?> getRelatedAccommodations(Pageable pageable,
                                                      @Parameter(name = "startDate", description = "숙박 시작 날짜, 2023-11-21", in = QUERY, required = true)
                                                      @RequestParam(value = "startDate", required = true)
                                                      LocalDate startDate,
                                                      @Parameter(name = "endDate", description = "숙박 종료 날짜, 2023-11-22", in = QUERY, required = true)
                                                      @RequestParam(value = "endDate", required = true)
                                                      LocalDate endDate,
                                                      @Parameter(name = "guest", description = "숙박 인원 수", in = QUERY, required = true)
                                                      @RequestParam(value = "guest", required = true)
                                                      Long guest,
                                                      @Parameter(name = "category", description = "숙박 상품 카테고리 ex) 호텔_리조트, 펜션_빌라, 모텔", in = QUERY, required = true)
                                                      @RequestParam("category") String category,
                                                      @Parameter(name = "region", description = "숙박 상품 지역 ex) 서울, 강원, 경기, 제주", in = QUERY, required = true)
                                                      @RequestParam("region") String region) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, accommodationService.getRelatedAccommodations(startDate, endDate, guest, category, region, pageable)));
    }

    @Operation(summary = "지역별 추천 상품 조회 API", description = "지역별 추천 상품 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = AccommodationResponse.class)))
    @GetMapping("/region")
    public ResponseEntity<?> getRecommendedRegionAccommodation(Pageable pageable,
                                                               @Parameter(name = "startDate", description = "숙박 시작 날짜, 2023-11-21", in = QUERY, required = true)
                                                               @RequestParam(value = "startDate", required = true)
                                                               LocalDate startDate,
                                                               @Parameter(name = "endDate", description = "숙박 종료 날짜, 2023-11-22", in = QUERY, required = true)
                                                               @RequestParam(value = "endDate", required = true)
                                                               LocalDate endDate,
                                                               @Parameter(name = "guest", description = "숙박 인원 수", in = QUERY, required = true)
                                                               @RequestParam(value = "guest", required = true)
                                                               Long guest,
                                                               @Parameter(name = "region", description = "검색할 지역 ex) 서울, 강원, 경기, 제주", in = QUERY, required = true)
                                                               @RequestParam("region")
                                                               String region) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, accommodationService.getRecommendedRegionAccommodation(startDate, endDate, guest, region, pageable)));
    }

    @Operation(summary = "카테고리별 상품 조회 API", description = "카테고리별 상품 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = AccommodationResponse.class)))
    @GetMapping("/category")
    public ResponseEntity<?> getCategoryAccommodations(Pageable pageable,
                                                       @Parameter(name = "startDate", description = "숙박 시작 날짜, 2023-11-21", in = QUERY, required = true)
                                                       @RequestParam(value = "startDate", required = true)
                                                       LocalDate startDate,
                                                       @Parameter(name = "endDate", description = "숙박 종료 날짜, 2023-11-22", in = QUERY, required = true)
                                                       @RequestParam(value = "endDate", required = true)
                                                       LocalDate endDate,
                                                       @Parameter(name = "guest", description = "숙박 인원 수", in = QUERY, required = true)
                                                       @RequestParam(value = "guest", required = true)
                                                       Long guest,
                                                       @Parameter(name = "category", description = "검색할 카테고리 ex) 호텔_리조트, 펜션_빌라, 모텔", in = QUERY, required = true)
                                                       @RequestParam("category")
                                                       String category) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, accommodationService.getAccommodationsByCategory(startDate, endDate, guest, category, pageable)));
    }

    @Operation(summary = "예약 랭킹 조회 API", description = "예약 랭킹 높은 순으로 숙소 조회 API 입니다")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = AccommodationResponse.class)))
    @GetMapping("/ranking")
    public ResponseEntity<?> getRankingAccommodations(Pageable pageable,
                                                      @Parameter(name = "startDate", description = "숙박 시작 날짜, 2023-11-21", in = QUERY, required = true)
                                                      @RequestParam(value = "startDate", required = true)
                                                      LocalDate startDate,
                                                      @Parameter(name = "endDate", description = "숙박 종료 날짜, 2023-11-22", in = QUERY, required = true)
                                                      @RequestParam(value = "endDate", required = true)
                                                      LocalDate endDate,
                                                      @Parameter(name = "guest", description = "숙박 인원 수", in = QUERY, required = true)
                                                      @RequestParam(value = "guest", required = true)
                                                      Long guest) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, accommodationService.getRankingAccommodations(startDate, endDate, guest, pageable)));
    }

    @Operation(summary = "숙박 상품 상세 조회", description = "숙박 상품 상세 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = AccommodationRoomOptionResponse.class)))
    @GetMapping("/{accommodationId}")
    public ResponseEntity<?> getAccommodationById(
            @Parameter(name = "startDate", description = "숙박 시작 날짜, 2023-11-21", in = QUERY, required = true)
            @RequestParam(value = "startDate", required = true)
            LocalDate startDate,
            @Parameter(name = "endDate", description = "숙박 종료 날짜, 2023-11-22", in = QUERY, required = true)
            @RequestParam(value = "endDate", required = true)
            LocalDate endDate,
            @Parameter(name = "guest", description = "숙박 인원 수", in = QUERY, required = true)
            @RequestParam(value = "guest", required = true)
            Long guest,
            @Parameter(name = "accommodationId", description = "숙박 상품 아이디", in = PATH)
            @PathVariable("accommodationId") Long accommodationId) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, accommodationService.findById(accommodationId, startDate, endDate, guest)));
    }

    @Operation(summary = "숙박 상품 룸옵션 상세 조회", description = "숙박 상품 룸 옵션 상세조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = RoomOptionResponse.class)))
    @GetMapping("/roomOptions/{roomOptionId}")
    public ResponseEntity<?> getAccommodationRoomOptionById(
            @Parameter(name = "startDate", description = "숙박 시작 날짜, 2023-11-21", in = QUERY, required = true)
            @RequestParam(value = "startDate", required = true)
            LocalDate startDate,
            @Parameter(name = "endDate", description = "숙박 종료 날짜, 2023-11-22", in = QUERY, required = true)
            @RequestParam(value = "endDate", required = true)
            LocalDate endDate,
            @Parameter(name = "guest", description = "숙박 인원 수", in = QUERY, required = true)
            @RequestParam(value = "guest", required = true)
            Long guest,
            @Parameter(name = "roomOptionId", description = "룸 옵션 아이디", in = PATH)
            @PathVariable("roomOptionId") Long roomOptionId) {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, accommodationService.findRoomOptionById(roomOptionId, startDate, endDate, guest)));

    }

}
