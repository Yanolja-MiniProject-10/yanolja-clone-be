package com.ybe.mp10.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ybe.mp10.domain.auth.dto.request.SignUpRequest;
import com.ybe.mp10.domain.user.dto.request.UpdatedUserRequest;
import com.ybe.mp10.domain.user.dto.response.UserResponse;
import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    public void init() {
        user = User.builder()
            .email("jh@test.com")
            .name("test")
            .isDeleted(false)
            .password("123456")
            .build();
    }

    @Test
    @DisplayName("회원가입 로직 테스트")
    void saveUser() {
        // given
        SignUpRequest signUpRequest = signUpRequest();

        // when
        Mockito.when(userRepository.save(any())).thenReturn(SignUpRequest.toEntity(signUpRequest));
        UserResponse userResponse = userService.saveUser(signUpRequest);

        // then
        assertThat(userResponse.getEmail()).isEqualTo(signUpRequest.getEmail());
        assertThat(userResponse.getName()).isEqualTo(signUpRequest.getName());
    }

    @Test
    @DisplayName("회원정보 수정 테스트")
    void updateUser() {
        // given
        given(userRepository.findByEmail("jh@test.com")).willReturn(Optional.ofNullable(user));

        UpdatedUserRequest updatedUserRequest = new UpdatedUserRequest();
        updatedUserRequest.setName("변경닉네임");

        // when
        UserResponse userResponse = userService.updateUser(user.getEmail(), updatedUserRequest);

        // then
        assertThat(userResponse.getName()).isEqualTo("변경닉네임");
    }

    private SignUpRequest signUpRequest() {
        return SignUpRequest.builder()
            .email("test@test.test")
            .password("123456")
            .name("테스터")
            .build();
    }
}