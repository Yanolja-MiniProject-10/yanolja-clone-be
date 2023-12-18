package com.ybe.mp10.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReissueRequest {
    @NotBlank(message = "빈 값을 넣을 수 없습니다.")
    private String refreshToken;
}
