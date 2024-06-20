package org.mrbonk97.fileshareserver.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.Folder;

@Getter
@Setter
@AllArgsConstructor
public class FolderCompactResponse {
    private String id;
    private String originalFileName;
    private String username;
    private Boolean heart = false;
    private String type = "FOLDER";

    public static FolderCompactResponse of(Folder folder) {
        return new FolderCompactResponse(
                folder.getId(),
                folder.getFolderName(),
                folder.getUser().getName(),
                folder.getHeart(),
                "FOLDER"
        );

    }
}
