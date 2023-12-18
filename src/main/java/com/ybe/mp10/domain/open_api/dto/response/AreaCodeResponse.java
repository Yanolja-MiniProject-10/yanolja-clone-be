package com.ybe.mp10.domain.open_api.dto.response;

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
@Schema(description = "지역 코드 응답 데이터")
public class AreaCodeResponse {

    List<AreaCode> areaCodes;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AreaCode {
        String code;
        String name;
    }
}


