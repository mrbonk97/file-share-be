package org.mrbonk97.fileshareserver.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.model.Folder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FolderResponse {
    private List<FileCompactResponse> files = new ArrayList<>();


    public static FolderResponse of(List<File> files, List<Folder> folders) {
        FolderResponse folderResponse = new FolderResponse();

        for(var e: files) folderResponse.files.add(FileCompactResponse.of(e));

        for(var e: folders) {
            FileCompactResponse fileCompactResponse = new FileCompactResponse();
            fileCompactResponse.setId(e.getId());
            fileCompactResponse.setOriginalFileName(e.getFolderName());
            fileCompactResponse.setUsername(e.getUser().getUsername());
            fileCompactResponse.setType("FOLDER");
            folderResponse.files.add(fileCompactResponse);
        }

        return folderResponse;
    }


}
