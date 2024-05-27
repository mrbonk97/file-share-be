package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.controller.request.CreateFolderRequest;
import org.mrbonk97.fileshareserver.controller.response.FileListResponse;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.service.FolderService;
import org.mrbonk97.fileshareserver.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/folders")
@RestController
public class FolderController {
    private final FolderService folderService;
    private final StorageService storageService;

    @PostMapping
    public void createFolder(@RequestBody CreateFolderRequest createFolderRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        folderService.createFolder(createFolderRequest.getFolderName(), createFolderRequest.getParentFolderId(), user);
    }

    @DeleteMapping("/{folderId}")
    public void deleteFolder(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        folderService.deleteFolder(folderId, user);
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<FileListResponse> getFilesInFolder(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Folder folder = folderService.loadById(folderId);
        List<File> files = storageService.getFilesByFolder(folder);
        return ResponseEntity.ok().body(FileListResponse.of(files));
    }

}
