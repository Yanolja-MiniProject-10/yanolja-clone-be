package com.ybe.mp10.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSearchRequest {

    @Schema(description = "이메일주소" , example = "jh77@naver.com")
    @Email(message = "이메일 양식으로 보내주세요.")
    private String email;

}
