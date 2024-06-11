package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<File, String> {
    List<File> findAllByUser(User user);

    List<File> findAllByFolder(Folder folder);

    List<File> findAllByOriginalFileNameLike(String fileName);

    Optional<File> findByCode(String code);

    @Query("select sum (a.size) from File a where a.user.id = :userId")
    Long findSumOfSizeByUserId(Long userId);
}
