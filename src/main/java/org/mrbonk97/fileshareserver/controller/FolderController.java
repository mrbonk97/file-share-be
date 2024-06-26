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
        if(createFolderRequest.getParentFolderId() == null || createFolderRequest.getParentFolderId().isEmpty()) {
            folder = folderService.createFolder(createFolderRequest.getFolderName(), user);
        }
        else
            folder = folderService.createFolder(createFolderRequest.getFolderName(), createFolderRequest.getParentFolderId(), user);

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
        List<File> files = storageService.getFilesInHome(user);
        List<Folder> folders = folderService.getAllFoldersInHome(user);
        log.info("최상위 폴더 조회");
        return ResponseEntity.ok().body(FolderResponse.of(files, folders));
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<FolderResponse> getFilesInFolder(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Folder folder = folderService.loadById(folderId);
        List<File> files = storageService.getFilesByFolder(folder);
        List<Folder> folders = folderService.getChildren(folderId);
        log.info("폴더 {} 내부 데이터 조회", folderId);
        return ResponseEntity.ok().body(FolderResponse.of(files, folders));
    }

    @PutMapping("/move-folder")
    public ResponseEntity<FolderCompactResponse> changeFolder(@RequestBody MoveFolderRequest moveFolderRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Folder folder = folderService.changeFolder(moveFolderRequest.getFolderId(), moveFolderRequest.getParentFolderId(), user);
        log.info("파일의 폴더 변경");
        return ResponseEntity.ok().body(FolderCompactResponse.of(folder));
    }

    @GetMapping("/find-depth/{folderId}")
    public ResponseEntity<List<Map<String, String>>> getBreadCrumb(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Map<String,String>> folderDepth = folderService.getFolderDepth(folderId);
        log.info("파일의 depth 확인함");
        return ResponseEntity.ok().body(folderDepth);
    }

    @PatchMapping("/heart/{folderId}")
    public ResponseEntity<FolderCompactResponse> changeHeartState(@PathVariable String folderId) {
        Folder folder = folderService.updateHeartState(folderId);
        log.info("폴더 하트 변경 {}", folderId);
        return ResponseEntity.ok().body(FolderCompactResponse.of(folder));
    }



}
