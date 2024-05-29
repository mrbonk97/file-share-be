package org.mrbonk97.fileshareserver.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.File;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UploadFileResponse {
    private final String hashedFileName;
    private final String originalFileName;
    private final String contentType;
    private final Long size;
    private final LocalDateTime updatedAt;
    private final String username;

    public static UploadFileResponse of(File file) {
        return new UploadFileResponse(
                file.getId(),
                file.getOriginalFileName(),
                file.getContentType(),
                file.getSize(),
                file.getUpdatedAt(),
                file.getUser().getName()
        );
    }
}
