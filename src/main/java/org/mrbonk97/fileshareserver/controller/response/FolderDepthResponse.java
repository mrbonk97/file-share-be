package org.mrbonk97.fileshareserver.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.dto.FolderDepthDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class FolderDepthResponse {
    List<FolderDepthDto> breadCrumbs = new ArrayList<>();

    public static FolderDepthResponse of(List<FolderDepthDto> folderDepthDtos) {
        FolderDepthResponse folderDepthResponse = new FolderDepthResponse();
        for(var e: folderDepthDtos)
            folderDepthResponse.getBreadCrumbs().add(e);

        return folderDepthResponse;
    }

}
