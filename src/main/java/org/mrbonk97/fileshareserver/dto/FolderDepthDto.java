package org.mrbonk97.fileshareserver.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FolderDepthDto {
    private final String folderName;
    private final String folderId;
}
