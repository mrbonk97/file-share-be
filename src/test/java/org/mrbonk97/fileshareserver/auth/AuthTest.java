package org.mrbonk97.fileshareserver.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AuthTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("정상 회원가입 기능 테스트")
    void createAccount() {
        // given
        User user = new User();
        user.setEmail("testEmail123@naver.com");
        user.setPassword("hashedPassword");
        user.setUsername("testUsername");

        // when
        User savedUser = accountRepository.save(user);

        // then
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    @DisplayName("이메일 중복 회원가입 기능 테스트")
    void duplicateEmailErrorTest() {
        // given
        User user = new User();
        user.setEmail("testEmail123@naver.com");
        user.setPassword("hashedPassword");
        user.setUsername("testUsername");

        // when
        User savedUser = accountRepository.save(user);

        User user2 = new User();
        user2.setEmail("testEmail123@naver.com");
        user2.setPassword("hashedPassword123");
        user2.setUsername("testUsername123");

        // then
        User savedUser2 = accountRepository.save(user2);

    }
}
