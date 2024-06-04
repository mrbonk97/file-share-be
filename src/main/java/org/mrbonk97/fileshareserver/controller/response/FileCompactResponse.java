package org.mrbonk97.fileshareserver.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.File;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class FileCompactResponse{
    private String id;
    private String originalFileName;
    private String contentType;
    private Long size;
    private LocalDateTime updatedAt;
    private String username;
    private String type;
    private String code;

    public static FileCompactResponse of(File file) {
        return new FileCompactResponse(
                file.getId(),
                file.getOriginalFileName(),
                file.getContentType(),
                file.getSize(),
                file.getUpdatedAt(),
                file.getUser().getUsername(),
                "FILE",
                file.getCode()
        );
    }
}
