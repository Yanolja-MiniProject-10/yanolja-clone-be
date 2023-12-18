package com.ybe.mp10.global.common.annotation;

import com.ybe.mp10.global.util.MockUserFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@WithSecurityContext(factory = MockUserFactory.class)
public @interface WithMockUser {
    String userId() default "1";

    String userName() default "userName";

    String role() default "ROLE_USER";
}
