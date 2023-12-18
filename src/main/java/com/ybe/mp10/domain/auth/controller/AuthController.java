package com.ybe.mp10.domain.auth.controller;

import static com.ybe.mp10.global.common.constant.ResponseConstant.CREATED;
import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;

import com.ybe.mp10.domain.auth.dto.request.EmailSearchRequest;
import com.ybe.mp10.domain.auth.dto.request.LoginRequest;
import com.ybe.mp10.domain.auth.dto.request.SignUpRequest;
import com.ybe.mp10.domain.auth.dto.response.EmailSearchResponse;
import com.ybe.mp10.domain.user.dto.response.UserResponse;
import com.ybe.mp10.domain.user.service.UserService;
import com.ybe.mp10.global.response.GlobalDataResponse;
import com.ybe.mp10.global.security.service.RefreshTokenService;
import com.ybe.mp10.global.security.token.BodyToken;
import com.ybe.mp10.domain.auth.dto.request.ReissueRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원인증 API", description = "회원인증 관련 API 입니다.")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "사용자 아이디 중복체크 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("checkEmail")
    public GlobalDataResponse<EmailSearchResponse> checkEmail(
        @Valid @Parameter @RequestBody EmailSearchRequest emailSearchRequest) {
        return GlobalDataResponse.ok(SUCCESS, new EmailSearchResponse(userService.checkedEmail(emailSearchRequest.getEmail())));
    }

    @Operation(summary = "회원가입 API")
    @ApiResponse(responseCode = "201", description = "회원가입 성공시")
    @PostMapping("/signup")
    public GlobalDataResponse<UserResponse> signUp(@Valid @Parameter @RequestBody SignUpRequest signUpRequest) {
        return GlobalDataResponse.created(CREATED, userService.saveUser(signUpRequest));
    }

    @Operation(summary = "로그인 API")
    @ApiResponse(responseCode = "200", description = "OK", headers = {})
    @PostMapping("/login")
    public ResponseEntity<?> login(@Parameter @RequestBody LoginRequest loginRequest) {
        throw new RuntimeException("로그인은 시큐리티 필터로 이루어져야 합니다.");
    }

    @Operation(summary = "로그아웃 API")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "토큰 재발급")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("/reissues")
    public GlobalDataResponse<BodyToken> reissue(@Valid @RequestBody ReissueRequest reissueToken) {
        return GlobalDataResponse.ok(SUCCESS, refreshTokenService.renewJwtToken(reissueToken));
    }
}
