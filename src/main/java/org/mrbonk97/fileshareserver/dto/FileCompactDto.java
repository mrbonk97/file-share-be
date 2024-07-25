package org.mrbonk97.fileshareserver.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.File;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class FileCompactDto {
    private final String id;
    private final String originalFilename;
    private final String contentType;
    private final Long size;
    private final LocalDateTime updatedAt;
    private final String username;
    private final String code;
    private final Boolean heart;

    public static FileCompactDto of(File file) {
        return new FileCompactDto(
                file.getId(),
                file.getOriginalFileName(),
                file.getContentType(),
                file.getSize(),
                file.getUpdatedAt(),
                file.getUser().getUsername(),
                file.getCode(),
                file.getHeart()
        );
    }

}
