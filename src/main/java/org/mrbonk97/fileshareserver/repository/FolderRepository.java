package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, String> {
    List<Folder> findAllByParentFolderId(String folderId);
}
