package com.ybe.mp10.domain.category.dto.response;

import com.ybe.mp10.domain.category.model.Category;
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
@Schema(description = "전체 카테고리 조회시 리턴 데이터")
public class GetAllCategory {
    List<String> categories;
}
