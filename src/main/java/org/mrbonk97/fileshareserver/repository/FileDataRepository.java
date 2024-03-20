package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, String> {
}
