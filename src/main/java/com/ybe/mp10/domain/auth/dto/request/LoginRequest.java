package com.ybe.mp10.domain.auth.dto.request;

import com.ybe.mp10.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.model.Region;
import com.ybe.mp10.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    //@Email
    @Schema(description = "이메일주소" , example = "jh77@naver.com")
    private String email;
    //@NotBlank
    @Schema(description = "비밀번호" , example = "12345")
    private String password;

    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
