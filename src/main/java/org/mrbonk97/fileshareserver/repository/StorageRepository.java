package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<File, String> {
    List<File> findAllByUser(User user);

    List<File> findAllByFolder(Folder folder);
}
