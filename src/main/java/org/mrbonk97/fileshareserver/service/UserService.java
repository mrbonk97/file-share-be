package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository accountRepository;

    public User loadByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new FileShareApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    public User loadById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new FileShareApplicationException(ErrorCode.USER_NOT_FOUND));
    }
    @Transactional
    public void deleteUser(User user) {
        accountRepository.delete(user);
    }

    @Transactional
    public void changeName(String username, User user) {
        user.setUsername(username);
        accountRepository.save(user);
    }
}
