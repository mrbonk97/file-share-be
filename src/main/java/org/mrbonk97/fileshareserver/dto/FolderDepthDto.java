package org.mrbonk97.fileshareserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class FolderDepthDto {
    private final String folderName;
    private final String folderId;
}
