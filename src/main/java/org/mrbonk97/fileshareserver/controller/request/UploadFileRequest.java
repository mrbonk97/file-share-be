package org.mrbonk97.fileshareserver.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadFileRequest {
    private MultipartFile file;
    private String folderId;
}
