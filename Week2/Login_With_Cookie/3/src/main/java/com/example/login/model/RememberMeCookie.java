package com.example.login.model;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class RememberMeCookie {

    private static final String NAME = "rememberMe";
    private static final String SEPARATOR = ":";
    private static final int MAX_AGE_SECONDS = 7 * 24 * 60 * 60;
    private static final int MAX_AGE_SESSION = -1;

    private RememberMeCookie() {
    }

    public record Credentials(String username, String password) {
    }

    public static void write(HttpServletRequest request, HttpServletResponse response,
                             String username, String password, boolean persistent) {
        Cookie cookie = new Cookie(NAME, encode(username) + SEPARATOR + encode(password));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(persistent ? MAX_AGE_SECONDS : MAX_AGE_SESSION);
        cookie.setPath(cookiePath(request));
        response.addCookie(cookie);
    }

    public static void clear(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(NAME, "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath(cookiePath(request));
        response.addCookie(cookie);
    }

    public static Credentials read(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (!NAME.equals(cookie.getName())) {
                continue;
            }

            String value = cookie.getValue();
            int separatorIndex = value == null ? -1 : value.indexOf(SEPARATOR);
            if (separatorIndex <= 0) {
                return null;
            }

            return new Credentials(
                    decode(value.substring(0, separatorIndex)),
                    decode(value.substring(separatorIndex + 1)));
        }
        return null;
    }

    private static String cookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.isEmpty() ? "/" : contextPath;
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
