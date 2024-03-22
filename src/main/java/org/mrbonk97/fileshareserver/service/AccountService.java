package org.mrbonk97.fileshareserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.repository.AccountRepository;
import org.mrbonk97.fileshareserver.repository.RefreshTokenRepository;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    public Account loadByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 계정 없음"));
    }

    public Account createAccount(String email, String password, String username) {
        accountRepository.findByEmail(email).ifPresent(s -> {throw new RuntimeException("이메일 중복");});

        Account account = new Account();
        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(bCryptPasswordEncoder.encode(password));
        account.setAccessToken(jwtUtils.generateAccessToken(email));

        return accountRepository.save(account);
    }

    public Account loginAccount(String email, String password) {
        Account account = loadByEmail(email);
        if(!bCryptPasswordEncoder.matches(password, account.getPassword())) throw new RuntimeException("패스워드 잘못됨");
        account.setAccessToken(jwtUtils.generateAccessToken(email));
        return account;
    }

    public void logoutAccount(String email) {
        Account account = loadByEmail(email);
        refreshTokenRepository.deleteByAccount(account);
    }

    public void deleteAccount(String email) {
        Account account = loadByEmail(email);
        accountRepository.delete(account);
    }
}
