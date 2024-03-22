package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.FileData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<FileData, String> {
    Optional<FileData> findByHashedFileName(String hashedFileName);
    Page<FileData> findByAccountOrderByCreatedAtDesc(Account account, Pageable pageable);
    Page<FileData> findByAccountOrderBySizeDesc(Account account, Pageable pageable);
}
