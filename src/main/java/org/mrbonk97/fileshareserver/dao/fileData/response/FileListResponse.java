package org.mrbonk97.fileshareserver.dao.fileData.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class FileListResponse {

    private static class Files {
        private String hashedFileName;
        private String originalFileName;
        private String contentType;
        private Long size;
        private byte [] fileData;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime scheduledDeleteDate;
    }

    List<Files> files = new ArrayList<>();




}
