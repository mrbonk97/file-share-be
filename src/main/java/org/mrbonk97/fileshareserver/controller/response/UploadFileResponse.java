package org.mrbonk97.fileshareserver.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.File;

@Getter
@RequiredArgsConstructor
public class UploadFileResponse {
    private final String response;

    public static UploadFileResponse of(File file) {
        return new UploadFileResponse(
                file.getOriginalFileName()
        );
    }
}
