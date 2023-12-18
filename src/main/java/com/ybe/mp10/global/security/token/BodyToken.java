package com.ybe.mp10.global.security.token;

import static com.ybe.mp10.global.common.constant.SecurityConstant.TOKEN_TYPE;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BodyToken {

    private String accessToken;
    private String refreshToken;

    public BodyToken(String accessToken, String refreshToken) {
        this.accessToken = TOKEN_TYPE + accessToken;
        this.refreshToken = TOKEN_TYPE + refreshToken;
    }
}
