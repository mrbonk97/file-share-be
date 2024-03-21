package org.mrbonk97.fileshareserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.repository.AccountRepository;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    public Account createAccount(String email, String password, String username) {
        accountRepository.findByEmail(email).ifPresent(s -> {throw new RuntimeException("이메일 중복");});

        Account account = new Account();
        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(bCryptPasswordEncoder.encode(password));

        return accountRepository.save(account);
    }

    public Account loginAccount(String email, String password) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 계정 없음"));
        if(!bCryptPasswordEncoder.matches(password, account.getPassword())) throw new RuntimeException("패스워드 잘못됨");

        return account;
    }

    @Transactional
    public void deleteAccount(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 계정 없음"));
        accountRepository.delete(account);
    }

    public Account loadByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 계정 없음"));
    }
}
