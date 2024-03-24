package org.mrbonk97.fileshareserver.dao.fileData.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.FileData;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UploadFileResponse {
    private final String hashedFilename;
    private final String originalFilename;
    private final String contentType;
    private final Long size;
    private final LocalDateTime createdAt;
    private final LocalDate scheduledDeleteDate;

    public static UploadFileResponse of(FileData fileData) {
        return new UploadFileResponse(
                fileData.getHashedFileName(),
                fileData.getOriginalFileName(),
                fileData.getContentType(),
                fileData.getSize(),
                fileData.getCreatedAt(),
                fileData.getScheduledDeleteDate());
    }
}
