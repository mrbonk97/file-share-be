package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.repository.AccountRepository;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final RedisService redisService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    public Account loadByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new FileShareApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    public Account createAccount(String email, String password, String username) {
        accountRepository.findByEmail(email).ifPresent(s -> {throw new FileShareApplicationException(ErrorCode.DUPLICATED_EMAIL);});

        Account account = new Account();
        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(bCryptPasswordEncoder.encode(password));
        account.setAccessToken(jwtUtils.generateAccessToken(email));
        account.setRefreshToken(jwtUtils.generateRefreshToken(email));

        return accountRepository.save(account);
    }

    public Account loginAccount(String email, String password) {
        Account account = loadByEmail(email);
        if(!bCryptPasswordEncoder.matches(password, account.getPassword())) throw new FileShareApplicationException(ErrorCode.USER_NOT_FOUND);
        account.setAccessToken(jwtUtils.generateAccessToken(email));
        account.setRefreshToken(jwtUtils.generateRefreshToken(email));
        return account;
    }

    @Transactional
    public void deleteAccount(String email) {
        accountRepository.delete(loadByEmail(email));
    }

    public void logoutFromAllDevice(String email) {
        redisService.setValue(email, LocalDateTime.now().toString(), Duration.ofDays(14L));
    }
}
