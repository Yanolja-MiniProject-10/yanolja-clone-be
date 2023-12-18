package com.ybe.mp10.domain.auth.controller;

import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_HEADER;
import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_TYPE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.mp10.domain.auth.dto.request.LoginRequest;
import com.ybe.mp10.domain.auth.factory.UserFactory;
import com.ybe.mp10.domain.user.dto.request.UpdatedUserRequest;
import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.domain.user.repository.UserRepository;
import com.ybe.mp10.global.util.JwtUtil;
import com.ybe.mp10.global.security.service.RefreshTokenService;
import com.ybe.mp10.global.security.token.BodyToken;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private User user;
    private HttpHeaders headers;

    private HttpHeaders createTestAuthHeader(String email, List<GrantedAuthority> roles) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(
            TOKEN_HEADER,
            TOKEN_TYPE + jwtUtil.createToken(email, roles)
        );
        return headers;
    }

    @BeforeEach
    void beforeEach(){
        user = userRepository.save(UserFactory.createTestUserWithRandomPassword());

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("USER"));

        headers = createTestAuthHeader(user.getEmail(), roles);
    }

    @Test
    @DisplayName("로그인하고 내 정보 조회하고 수정하고 마지막으로 토큰 오류 발생 후 토큰 재발급")
    void loginProcess() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
            .email(user.getEmail())
            .password("123456").build();

        // 로그인
        ResultActions resultActions = mvc.perform(post("/auth/login")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)));

        resultActions.andExpect(status().isOk());

        // 내 정보 조회
        ResultActions users = mvc.perform(get("/users")
            .headers(headers));
        users.andExpect(status().isOk());

        UpdatedUserRequest updatedUserRequest = new UpdatedUserRequest();
        updatedUserRequest.setName("변경한닉네");

        // 내 정보 수정
        ResultActions updatedUserAction = mvc.perform(put("/users")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedUserRequest)));

        updatedUserAction.andExpect(status().isOk());

        // 재발급 예외 호출
        ResultActions reissueAction = mvc.perform(get("/users")
                .header(TOKEN_HEADER,TOKEN_TYPE+"123sadcffeerf")
            );

        reissueAction.andExpect(status().isForbidden());

        // 토큰 재발급
        String refreshToken = jwtUtil.createRefreshToken(user.getEmail());

        refreshTokenService.updateRefreshToken(user.getEmail(), refreshToken);

        BodyToken bodyToken = new BodyToken(null, refreshToken);

        ResultActions reissuesAction = mvc.perform(post("/auth/reissues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bodyToken)));

        reissuesAction.andExpect(status().isOk());
    }
}