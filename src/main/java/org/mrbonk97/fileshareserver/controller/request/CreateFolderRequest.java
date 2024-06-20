package org.mrbonk97.fileshareserver.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CreateFolderRequest {
    private final String folderName;
    private final String folderId;
}
