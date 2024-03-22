package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    public Optional<RefreshToken> findByAccount(Account account);
    public void deleteByAccount(Account account);
}
