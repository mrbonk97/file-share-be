package org.mrbonk97.fileshareserver.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChangeFolderRequest {
    private final String fileId;
    private final String folderId;
}
