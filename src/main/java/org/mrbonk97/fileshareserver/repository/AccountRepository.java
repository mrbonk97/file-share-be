package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
