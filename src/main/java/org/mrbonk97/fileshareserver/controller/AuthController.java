package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.service.AccountService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AccountService accountService;

//    @PostMapping("/sign-in")
//    public Response<LoginAccountResponse> signIn(@RequestBody LoginAccountRequest loginAccountRequest) {
//        User user = accountService.loginAccount(loginAccountRequest.getEmail().trim(), loginAccountRequest.getPassword().trim());
//        log.info("유저 로그인: {}", user.getEmail().trim());
//        return Response.success(LoginAccountResponse.of(user));
//    }

}
