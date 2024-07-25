package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.controller.request.ChangeUsernameRequest;
import org.mrbonk97.fileshareserver.controller.response.Response;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.service.UserService;
import org.mrbonk97.fileshareserver.utils.CookieUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
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
}
