package org.mrbonk97.fileshareserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.controller.request.ChangeUsernameRequest;
import org.mrbonk97.fileshareserver.controller.response.Response;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.service.UserService;
import org.mrbonk97.fileshareserver.utils.CookieUtils;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
    @Value("${oauth2.redirect.url}")
    private String REDIRECT_URL;
    private final UserService userService;

    @GetMapping("/me")
    public Response<User> getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 유저 정보 조회", user.getId());

        return Response.success(user);
    }

    @DeleteMapping("/me")
    public Response<String> deleteUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 회원 탈퇴", user.getId());

        userService.deleteUser(user);
        return Response.success("유저 계정 삭제 완료: " + user.getId());
    }

    @PatchMapping("/me/change-name")
    public Response<User> changeName(@RequestBody ChangeUsernameRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User changedUser = userService.changeName(request.getUsername(), user);
        return Response.success(changedUser);
    }

    @GetMapping("/me/sign-out")
    public ResponseEntity<String> signOut() {
        log.info("유저 로그아웃");
        ResponseCookie cookie = CookieUtils.deleteRefreshToken();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body("로그아웃 완료");
    }
    
    @GetMapping("/test-sign-in")
    public void testSignIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("테스트 계정으로 로그인");

        String accessToken =
                JwtUtils.generateAccessToken(1L);

        String refreshToken =
                JwtUtils.generateRefreshToken(1L);

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        ResponseCookie cookie = CookieUtils.generateRefreshToken(refreshToken);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        String uri = UriComponentsBuilder.fromUriString(REDIRECT_URL)
                .queryParam("access_token", URLEncoder.encode(accessToken, StandardCharsets.UTF_8))
                .build()
                .toUriString();


        redirectStrategy.sendRedirect(request, response, uri);
    }
}
