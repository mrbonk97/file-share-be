package org.mrbonk97.fileshareserver.auth;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mrbonk97.fileshareserver.model.Account;
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
        Account account = new Account();
        account.setEmail("testEmail123@naver.com");
        account.setPassword("hashedPassword");
        account.setUsername("testUsername");

        // when
        Account savedAccount = accountRepository.save(account);

        // then
        Assertions.assertThat(savedAccount.getId()).isNotNull();
    }

    @Test
    @DisplayName("이메일 중복 회원가입 기능 테스트")
    void duplicateEmailErrorTest() {
        // given
        Account account = new Account();
        account.setEmail("testEmail123@naver.com");
        account.setPassword("hashedPassword");
        account.setUsername("testUsername");

        // when
        Account savedAccount = accountRepository.save(account);

        Account account2 = new Account();
        account2.setEmail("testEmail123@naver.com");
        account2.setPassword("hashedPassword123");
        account2.setUsername("testUsername123");

        // then
        Account savedAccount2 = accountRepository.save(account2);

    }
}
