package com.ybe.mp10.domain.user.service;


import com.ybe.mp10.domain.auth.dto.request.SignUpRequest;
import com.ybe.mp10.domain.user.dto.request.UpdatedUserRequest;
import com.ybe.mp10.domain.user.dto.response.UserResponse;
import com.ybe.mp10.domain.user.exception.DuplicateEmailException;
import com.ybe.mp10.domain.user.exception.UserException;
import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean checkedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return true;
        }
        return false;
    }

    @Transactional
    public UserResponse saveUser(SignUpRequest signUpRequest) {
        if (checkEmailDuplicate(signUpRequest.getEmail())) {
            throw new DuplicateEmailException("해당 이메일이 이미 존재합니다.", HttpStatus.BAD_REQUEST);
        }
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return UserResponse.toResponse(userRepository.save(SignUpRequest.toEntity(signUpRequest)));
    }

    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserException("사용자가 없습니다.", HttpStatus.UNAUTHORIZED));
        return UserResponse.toResponse(user);
    }

    @Transactional
    public UserResponse updateUser(String email, UpdatedUserRequest updatedUserRequest) {
        User currUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserException("사용자가 없습니다.", HttpStatus.UNAUTHORIZED));
        currUser.updateUser(updatedUserRequest.getName());
        return UserResponse.toResponse(currUser);
    }

    @Transactional
    public boolean deleteUser(String email) {
        User currUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserException("사용자가 없습니다.", HttpStatus.UNAUTHORIZED));
        currUser.deleteUser();
        return true;
    }

    private boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }


}
