package com.ybe.mp10.global.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieUtil {
    public static Cookie createCookie(String cookieName, String value, int maxAge){
        Cookie token = new Cookie(cookieName,value);
        // token.setDomain("ybe-mini.site");
        // token.setDomain("localhost");
        token.setHttpOnly(true);
        token.setSecure(true);
        token.setMaxAge(maxAge);
        token.setPath("/");
        token.setAttribute("SameSite", "None");
        return token;
    }
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
        String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    public static Optional<Cookie> getCookie(HttpServletRequest req, String cookieName){
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }
}
