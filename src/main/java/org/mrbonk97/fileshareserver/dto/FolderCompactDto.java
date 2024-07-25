package org.mrbonk97.fileshareserver.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.Folder;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class FolderCompactDto {
    private final String id;
    private final String folderName;
    private final String parentFolderId;
    private final String username;
    private final Boolean heart;
    private final LocalDateTime updatedAt;

    public static FolderCompactDto of(final Folder folder) {
        return new FolderCompactDto(
                folder.getId(),
                folder.getFolderName(),
                folder.getParentFolder() == null ? null : folder.getParentFolder().getId(),
                folder.getUser().getName(),
                folder.getHeart(),
                folder.getUpdatedAt()
        );
    }

}
