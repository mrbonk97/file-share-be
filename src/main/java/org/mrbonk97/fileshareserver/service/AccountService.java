package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.repository.AccountRepository;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User loadByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new FileShareApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    public User loadById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new FileShareApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    public User loginAccount(String email, String password) {
        User user = loadByEmail(email);
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) throw new FileShareApplicationException(ErrorCode.USER_NOT_FOUND);
        user.setAccessToken(JwtUtils.generateAccessToken(email));
        user.setRefreshToken(JwtUtils.generateRefreshToken(email));
        return user;
    }

    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

}
