package org.mrbonk97.fileshareserver.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.dto.FileCompactDto;
import org.mrbonk97.fileshareserver.dto.FolderCompactDto;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.model.Folder;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class FileFolderResponse {
    private final List<FileCompactDto> files;
    private final List<FolderCompactDto> folders;

    public static FileFolderResponse fromFiles(List<File> _files) {
        List<FileCompactDto> files = _files.stream().map(FileCompactDto::of).toList();
        return new FileFolderResponse(files, new ArrayList<>());
    }
    public static FileFolderResponse fromFolders(List<Folder> _folders) {
        List<FolderCompactDto> folders = _folders.stream().map(FolderCompactDto::of).toList();
        return new FileFolderResponse(new ArrayList<>(), folders);
    }

    public static FileFolderResponse of(final List<File> _files, final List<Folder> _folders) {
        List<FileCompactDto> files = _files.stream().map(FileCompactDto::of).toList();
        List<FolderCompactDto> folders = _folders.stream().map(FolderCompactDto::of).toList();
        return new FileFolderResponse(files, folders);
    }



}
