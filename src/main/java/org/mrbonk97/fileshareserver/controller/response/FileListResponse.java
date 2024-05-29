package org.mrbonk97.fileshareserver.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.File;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class FileListResponse {
    @Getter
    @AllArgsConstructor
    private static class FileResponse{
        private String hashedFileName;
        private String originalFileName;
        private String contentType;
        private Long size;
        private LocalDateTime updatedAt;
        private String username;
        private String type;
    }

    List<FileResponse> files;

    public static FileListResponse of(List<File> files2) {
        FileListResponse fileListResponse = new FileListResponse();
        List<FileResponse> files = new ArrayList<>();

        for(var e: files2) {
            FileResponse fileResponse = new FileResponse(
                    e.getId(),
                    e.getOriginalFileName(),
                    e.getContentType(),
                    e.getSize(),
                    e.getUpdatedAt(),
                    e.getUser().getUsername(),
                    "FILE"
            );
            files.add(fileResponse);
        }

        fileListResponse.setFiles(files);
        return fileListResponse;
    }
}
