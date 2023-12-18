package com.ybe.mp10.global.util;

import static com.ybe.mp10.global.common.constant.SecurityConstant.*;

import com.ybe.mp10.global.security.exception.BadTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userPK,
        Collection<? extends GrantedAuthority> authorities) {
        Date expiryDate = new Date(new Date().getTime() + ACCESS_TOKEN_VALID_TIME);

        return Jwts.builder()
            .setSubject(userPK)
            .claim(USER_KEY, userPK)
            .claim(USER_ROLES, authorities.stream().map(GrantedAuthority::getAuthority)
                .toArray(String[]::new))
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .signWith(secretKey, SIGNATURE_ALGORITHM)
            .compact();
    }

    public Claims verifyToken(String jwtToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken)
                .getBody();
        } catch (JwtException e) {
            throw new BadTokenException(e.getMessage());
        }
    }

    public String createRefreshToken(String userPK) {
        Date expiryDate = new Date(new Date().getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
            .setSubject(userPK)
            .claim(USER_KEY, userPK)
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .signWith(secretKey, SIGNATURE_ALGORITHM)
            .compact();
    }
}
