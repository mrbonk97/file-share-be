package org.mrbonk97.fileshareserver.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MoveFolderRequest {
    private final String folderId;
    private final String parentFolderId;
}
