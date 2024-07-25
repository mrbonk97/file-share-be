package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.controller.request.CreateFolderRequest;
import org.mrbonk97.fileshareserver.controller.request.MoveFolderRequest;
import org.mrbonk97.fileshareserver.controller.response.FileFolderResponse;
import org.mrbonk97.fileshareserver.controller.response.Response;
import org.mrbonk97.fileshareserver.dto.FolderCompactDto;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.service.FolderService;
import org.mrbonk97.fileshareserver.service.FileService;
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
    private final FileService fileService;

    @PostMapping
    public Response<FolderCompactDto> createFolder(@RequestBody CreateFolderRequest createFolderRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Folder createdFolder = folderService.createFolder(
                createFolderRequest.getFolderName(),
                createFolderRequest.getParentFolderId(),
                user);

        log.info("유저: {} 폴더 생성: {}", user.getId(), createdFolder.getId());
        return Response.success(FolderCompactDto.of(createdFolder));
    }

    @DeleteMapping("/{folderId}")
    public Response<String> deleteFolder(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 폴더 삭제: {}", user.getId(), folderId);

        folderService.deleteFolder(folderId, user);
        return Response.success("폴더 삭제 완료: " + folderId);
    }

    @GetMapping
    public Response<FileFolderResponse> getFilesInFolder(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 최상위 폴더 조회", user.getId());

        List<File> files = fileService.getFilesInHome(user);
        List<Folder> folders = folderService.getAllFoldersInHome(user);
        return Response.success(FileFolderResponse.of(files, folders));
    }

    @GetMapping("/{folderId}")
    public Response<FileFolderResponse> getFilesInFolder(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 폴더 조회: {}",user.getId(), folderId);

        Folder folder = folderService.loadById(folderId);
        List<File> files = fileService.getFilesByFolder(folder);
        List<Folder> folders = folderService.getAllChildrenFoldersById(folderId);

        return Response.success(FileFolderResponse.of(files, folders));
    }

    @PutMapping("/move-folder")
    public Response<FolderCompactDto> changeFolder(@RequestBody MoveFolderRequest moveFolderRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 폴더 위치 변경 {} -> {}",
                user.getId(),
                moveFolderRequest.getFolderId(),
                moveFolderRequest.getParentFolderId());

        Folder folder = folderService.changeFolder(
                moveFolderRequest.getFolderId(),
                moveFolderRequest.getParentFolderId(),
                user);

        return Response.success(FolderCompactDto.of(folder));
    }

    @GetMapping("/find-depth/{folderId}")
    public Response<List<Map<String, String>>> getBreadCrumb(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 폴더 depth 조회: {}", user.getId(), folderId);

        List<Map<String,String>> folderDepth = folderService.getFolderDepth(folderId);
        return Response.success(folderDepth);
    }

    @PatchMapping("/heart/{folderId}")
    public Response<FolderCompactDto> changeHeartState(@PathVariable String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("유저: {} 폴더 하트 변경 {}",user.getId(), folderId);

        Folder folder = folderService.updateHeartState(folderId, user);
        return Response.success(FolderCompactDto.of(folder));
    }

}
