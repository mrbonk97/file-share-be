package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.dto.Response;
import org.mrbonk97.fileshareserver.dto.account.request.LoginAccountRequest;
import org.mrbonk97.fileshareserver.dto.account.request.RegisterAccountRequest;
import org.mrbonk97.fileshareserver.dto.account.response.LoginAccountResponse;
import org.mrbonk97.fileshareserver.dto.account.response.RegisterAccountResponse;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.service.AccountService;
import org.mrbonk97.fileshareserver.service.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account")
@RestController
public class AccountController {
    private final AccountService accountService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/sign-up")
    public Response<RegisterAccountResponse> createAccount(@RequestBody RegisterAccountRequest registerAccountRequest){
        Account account = accountService.createAccount(
                registerAccountRequest.getEmail(),
                registerAccountRequest.getPassword(),
                registerAccountRequest.getUsername());

        refreshTokenService.createRefreshToken(account);

        return Response.success(RegisterAccountResponse.of(account));
    }

    @PostMapping("/sign-in")
    public Response<LoginAccountResponse> loginAccount(@RequestBody LoginAccountRequest loginAccountRequest) {
        Account account = accountService.loginAccount(loginAccountRequest.getEmail(), loginAccountRequest.getPassword());
        String accessToken = refreshTokenService.refreshAccessToken(account);
        log.info(account.getEmail() + "사용자가 로그인 " + accessToken);
        return Response.success(LoginAccountResponse.of(account, accessToken));
    }

    @GetMapping("/logout")
    public Response<String> logoutAccount(Authentication authentication) {
        String email = authentication.getName();
        Account account = accountService.loadByEmail(email);
        refreshTokenService.deleteRefreshToken(account);
        return Response.success("Successfully logout");
    }





}
