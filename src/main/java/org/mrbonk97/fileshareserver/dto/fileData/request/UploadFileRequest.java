package org.mrbonk97.fileshareserver.dto.fileData.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class UploadFileRequest {
    private final String title;
    private final MultipartFile file;
}
