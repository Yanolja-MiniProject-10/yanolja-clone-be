package com.ybe.mp10.domain.user.dto.response;

import com.ybe.mp10.domain.auth.dto.request.SignUpRequest;
import com.ybe.mp10.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    @Schema(description = "식별자" , example = "1")
    private Long id;
    @Schema(description = "이메일주소" , example = "jh77@naver.com")
    private String email;
    @Schema(description = "이름" , example = "jaehyuk")
    private String name;

    @Builder
    public UserResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .build();
    }


}
