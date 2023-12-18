package com.ybe.mp10.global.common.constant;

import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityConstant {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final String USER_KEY = "user-key";
    public static final String USER_ROLES = "roles";
    // public static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7;
    public static final long ACCESS_TOKEN_VALID_TIME = 1000L * 30;
    // public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30;
    public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 10;
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
}
