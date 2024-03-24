package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.dao.Response;
import org.mrbonk97.fileshareserver.dao.account.request.LoginAccountRequest;
import org.mrbonk97.fileshareserver.dao.account.request.RegisterAccountRequest;
import org.mrbonk97.fileshareserver.dao.account.response.DeleteAccountResponse;
import org.mrbonk97.fileshareserver.dao.account.response.LoginAccountResponse;
import org.mrbonk97.fileshareserver.dao.account.response.LogoutAccountResponse;
import org.mrbonk97.fileshareserver.dao.account.response.RegisterAccountResponse;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AccountService accountService;

    @PostMapping("/sign-up")
    public Response<RegisterAccountResponse> signUp(@RequestBody RegisterAccountRequest registerAccountRequest){
        Account account = accountService.createAccount(
                registerAccountRequest.getEmail().trim(),
                registerAccountRequest.getPassword().trim(),
                registerAccountRequest.getUsername().trim());

        log.info("신규 유저 가입: {}", account.getEmail().trim());
        return Response.success(RegisterAccountResponse.of(account));
    }

    @PostMapping("/sign-in")
    public Response<LoginAccountResponse> signIn(@RequestBody LoginAccountRequest loginAccountRequest) {
        Account account = accountService.loginAccount(loginAccountRequest.getEmail().trim(), loginAccountRequest.getPassword().trim());
        log.info("유저 로그인: {}", account.getEmail().trim());
        return Response.success(LoginAccountResponse.of(account));
    }

    @GetMapping("/sign-out-all")
    public Response<LogoutAccountResponse> signOut(Authentication authentication) {
        String email = authentication.getName();
        accountService.logoutFromAllDevice(email);
        log.info("유저 로그아웃: {}", email);
        return Response.success(LogoutAccountResponse.of("Logged out From All Devices successfully"));
    }

    @DeleteMapping("/delete")
    public Response<DeleteAccountResponse> deleteAccount(Authentication authentication) {
        String email = authentication.getName();
        accountService.deleteAccount(email);
        log.info("유저 삭제: {}", email);
        return Response.success(DeleteAccountResponse.of("Account Deleted successfully"));
    }

}
