package com.ybe.mp10.domain.auth.dto.request;

import com.ybe.mp10.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    @Schema(description = "이메일주소" , example = "jh77@naver.com")
    @Email(message = "이메일 양식으로 보내주세요.")
    private String email;

    @Schema(description = "비밀번호" , example = "12345")
    @NotBlank
    private String password;

    @Schema(description = "이름" , example = "jaehyuk")
    @NotBlank
    @Size(min=2, max = 10)
    @Pattern(regexp = "[가-힣a-zA-Z0-9]+", message = "특수문자는 사용할 수 없습니다.")
    private String name;

    @Builder
    public SignUpRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User toEntity(SignUpRequest signUpRequest) {
        return User.builder()
            .email(signUpRequest.getEmail())
            .password(signUpRequest.getPassword())
            .name(signUpRequest.getName())
            .isDeleted(false)
            .build();
    }
}
