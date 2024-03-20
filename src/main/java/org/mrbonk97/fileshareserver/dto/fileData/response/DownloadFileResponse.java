package org.mrbonk97.fileshareserver.dto.fileData.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DownloadFileResponse {
    private final byte [] bytes;

    public static DownloadFileResponse of(byte [] bytes) {
        return new DownloadFileResponse(bytes);
    }
}
