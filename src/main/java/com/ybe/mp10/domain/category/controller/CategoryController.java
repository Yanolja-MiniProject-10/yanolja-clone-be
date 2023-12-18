package com.ybe.mp10.domain.category.controller;

import com.ybe.mp10.domain.category.dto.response.GetAllCategory;
import com.ybe.mp10.domain.category.service.CategoryService;
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

@Tag(name = "카테고리 관련 API", description = "카테고리 관련 API 입니다")
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "전체 카테고리 조회 API", description = "전체 카테고리 조회 API 입니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공시", content = @Content(schema = @Schema(implementation = GetAllCategory.class)))
    @GetMapping("")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(GlobalDataResponse.ok(SUCCESS, categoryService.getCategories()));
    }
}
