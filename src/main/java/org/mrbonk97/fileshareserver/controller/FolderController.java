package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.controller.request.CreateFolderRequest;
import org.mrbonk97.fileshareserver.controller.request.MoveFolderRequest;
import org.mrbonk97.fileshareserver.controller.response.FolderCompactResponse;
import org.mrbonk97.fileshareserver.controller.response.FolderResponse;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.service.FolderService;
import org.mrbonk97.fileshareserver.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/folders")
@RestController
public class FolderController {
    private final FolderService folderService;
    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<FolderCompactResponse> createFolder(@RequestBody CreateFolderRequest createFolderRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Folder folder = null;
        if(createFolderRequest.getFolderId() == null) {
            folder = folderService.createFolder(createFolderRequest.getFolderName(), user);
        }
        else
            folder = folderService.createFolder(createFolderRequest.getFolderName(), createFolderRequest.getFolderId(), user);

        log.info("사용자 폴더 생성 {} : {}", user.getId(), createFolderRequest.getFolderName());
        return ResponseEntity.ok().body(FolderCompactResponse.of(folder));


    }

    @DeleteMapping("/{folderId}")
    public void deleteFolder(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        folderService.deleteFolder(folderId, user);
        log.info("사용자 폴더 삭제 {} : {}", user.getId(), folderId);
    }

    @GetMapping
    public ResponseEntity<FolderResponse> getFilesInFolder(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<File> files = storageService.getFilesByFolder(null);
        List<Folder> folders = folderService.getChildren(null);
        log.info("최상위 폴더 조회");
        System.out.println(files.size() + " " + folders.size());
        return ResponseEntity.ok().body(FolderResponse.of(files, folders));
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<FolderResponse> getFilesInFolder(@PathVariable String folderId, Authentication authentication) {
        log.info("폴더 {} 내부 데이터 조회", folderId);
        User user = (User) authentication.getPrincipal();
        Folder folder = folderService.loadById(folderId);
        List<File> files = storageService.getFilesByFolder(folder);
        List<Folder> folders = folderService.getChildren(folderId);
        return ResponseEntity.ok().body(FolderResponse.of(files, folders));
    }

    @PutMapping("/move-folder")
    public void changeFolder(@RequestBody MoveFolderRequest moveFolderRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        folderService.changeFolder(moveFolderRequest.getFolderId(), moveFolderRequest.getParentFolderId(), user);
        log.info("파일의 폴더 변경");
    }

    @GetMapping("/find-depth/{folderId}")
    public ResponseEntity<List<Map<String, String>>> getBreadCrumb(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Map<String,String>> folderDepth = folderService.getFolderDepth(folderId);
        log.info("파일의 depth 확인함");
        return ResponseEntity.ok().body(folderDepth);
    }



}
