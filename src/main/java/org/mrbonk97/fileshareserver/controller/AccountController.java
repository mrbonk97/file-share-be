package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.dto.Response;
import org.mrbonk97.fileshareserver.dto.account.request.LoginAccountRequest;
import org.mrbonk97.fileshareserver.dto.account.request.RegisterAccountRequest;
import org.mrbonk97.fileshareserver.dto.account.response.LoginAccountResponse;
import org.mrbonk97.fileshareserver.dto.account.response.RegisterAccountResponse;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public Response<RegisterAccountResponse> createAccount(@RequestBody RegisterAccountRequest registerAccountRequest){
        Account account = accountService.createAccount(
                registerAccountRequest.getEmail(),
                registerAccountRequest.getPassword(),
                registerAccountRequest.getUsername());

        return Response.success(RegisterAccountResponse.of(account));
    }

    @GetMapping
    public Response<LoginAccountResponse> loginAccount(@RequestBody LoginAccountRequest loginAccountRequest) {
        Account account = accountService.loginAccount(loginAccountRequest.getEmail(), loginAccountRequest.getPassword());
        return Response.success(LoginAccountResponse.of(account));
    }



}
