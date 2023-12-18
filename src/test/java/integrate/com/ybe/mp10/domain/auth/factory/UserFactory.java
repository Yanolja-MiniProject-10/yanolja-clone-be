package com.ybe.mp10.domain.auth.factory;

import com.ybe.mp10.domain.user.model.User;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFactory {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User createTestUserWithRandomPassword() {
        return User.builder()
            .name("멤버테스트" + ThreadLocalRandom.current().nextInt(1000000))
            .email("test" + ThreadLocalRandom.current().nextInt(1000000) + "@test.com")
            .password(passwordEncoder.encode("123456"))
            .isDeleted(false)
            .build();
    }

}
