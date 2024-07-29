package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.repository.StorageRepository;
import org.mrbonk97.fileshareserver.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository accountRepository;
    private final StorageRepository storageRepository;

    public User loadByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(() -> new FileShareApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    public User loadById(Long id) {
        User user = accountRepository.findById(id).orElseThrow(() -> new FileShareApplicationException(ErrorCode.USER_NOT_FOUND));
        user.setMemoryUsage(storageRepository.findSumOfSizeByUserId(id));
        // 파일이 없을 경우 0
        if(user.getMemoryUsage() == null) user.setMemoryUsage(0L);
        return user;
    }
    @Transactional
    public void deleteUser(User user) {
        accountRepository.delete(user);
    }

    @Transactional
    public User changeName(String username, User user) {
        user.setUsername(username);
        return accountRepository.save(user);
    }
}
