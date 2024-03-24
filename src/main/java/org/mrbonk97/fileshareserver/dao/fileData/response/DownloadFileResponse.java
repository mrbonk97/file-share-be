package org.mrbonk97.fileshareserver.dao.fileData.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.FileData;

@Getter
@RequiredArgsConstructor
public class DownloadFileResponse {
    private final byte [] bytes;

    public static DownloadFileResponse of(FileData fileData) {
        return new DownloadFileResponse(fileData.getDecompressedData());
    }
}
