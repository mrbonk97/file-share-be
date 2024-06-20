package org.mrbonk97.fileshareserver.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.File;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UploadFileResponse {
    private final String id;
    private final String originalFileName;
    private final String contentType;
    private final Long size;
    private final LocalDateTime updatedAt;
    private final String username;
    private final Boolean heart;
    private final String type = "FILE";

    public static UploadFileResponse of(File file) {
        return new UploadFileResponse(
                file.getId(),
                file.getOriginalFileName(),
                file.getContentType(),
                file.getSize(),
                file.getUpdatedAt(),
                file.getUser().getName(),
                file.getHeart()
        );
    }
}
