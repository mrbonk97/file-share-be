package org.mrbonk97.fileshareserver.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import java.util.Optional;

public class CookieUtils {
    final static int TWO_WEEK = 14 * 24 * 60 * 60;
    public static ResponseCookie deleteRefreshToken() {
        return ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .secure(false)
                .build();
    }

    public static ResponseCookie generateRefreshToken(String jwt) {
        return ResponseCookie
                .from("refresh_token", jwt)
                .httpOnly(true)
                .maxAge(TWO_WEEK)
                .path("/")
                .secure(false) // TODO : after Setting HTTPS, Change to true
                .build();

    }

    public static ResponseCookie generateAccessToken(String jwt) {
        return ResponseCookie
                .from("access_token", jwt)
                .httpOnly(false)
                .maxAge(TWO_WEEK)
                .path("/")
                .secure(false) // TODO : after Setting HTTPS, Change to true
                .build();

    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }


        return Optional.empty();
    }
}

