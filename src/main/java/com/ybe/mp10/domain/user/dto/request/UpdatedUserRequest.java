package com.ybe.mp10.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatedUserRequest {
    @NotBlank
    @Size(min=2, max = 10)
    @Pattern(regexp = "[가-힣a-zA-Z0-9]+", message = "특수문자는 사용할 수 없습니다.")
    private String name;
}
