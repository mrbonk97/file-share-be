package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.dto.Account.request.CreateAccountRequest;
import org.mrbonk97.fileshareserver.dto.Account.request.CreateAccountResponse;
import org.mrbonk97.fileshareserver.dto.Account.request.Response;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/account")
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping()
    public Response<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        Account account = accountService.createAccount(
                createAccountRequest.getEmail(),
                createAccountRequest.getPassword(),
                createAccountRequest.getUsername());

        return Response.success(CreateAccountResponse.of(account));
    }

}
