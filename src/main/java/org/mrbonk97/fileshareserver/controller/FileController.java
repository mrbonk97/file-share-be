package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.controller.request.ChangeFolderRequest;
import org.mrbonk97.fileshareserver.controller.response.FileFolderResponse;
import org.mrbonk97.fileshareserver.controller.response.Response;
import org.mrbonk97.fileshareserver.dto.FileCompactDto;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.service.FolderService;
import org.mrbonk97.fileshareserver.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/api/files")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final FileService fileService;
    private final FolderService folderService;

    @PostMapping
    public Response<FileCompactDto> uploadFile(@RequestParam MultipartFile file, @RequestParam(required = false) String folderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File uploadedFile = fileService.uploadFile(file, folderId, user);
        log.info("유저: {} 파일 저장. 파일: {}",user.getId(), uploadedFile.getId());
        return Response.success(FileCompactDto.of(uploadedFile));
    }

    @GetMapping("/preview/{filename}")
    public ResponseEntity<byte[]> preview(@PathVariable String filename, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File file = fileService.downloadFile(filename, user);
        log.info("유저: {} 파일 미리보기. 파일: {}",user.getId(), file.getId());
        return ResponseEntity.ok().contentType(MediaType.valueOf(file.getContentType())).body(file.getFileData());
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File file = fileService.downloadFile(filename, user);
        log.info("유저: {} 파일 다운로드. 파일: {}",user.getId(), file.getId());
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(file.getContentType()))
                .body(file.getFileData());
    }

    @DeleteMapping("/{filename}")
    public Response<String> deleteFile(@PathVariable String filename, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        fileService.deleteFile(filename, user);
        log.info("유저: {} 파일 삭제 요청 파일: {}",user.getId(), filename);
        return Response.success("파일 삭제 성공: " + filename);
    }

    @GetMapping
    public Response<List<FileCompactDto>> getFiles(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<File> _files = fileService.getFiles(user);
        List<FileCompactDto> files = _files.stream().map(FileCompactDto::of).toList();
        log.info("유저: {} 파일 목록 조회",user.getId());
        return Response.success(files);
    }

    @PutMapping("/change-folder")
    public Response<FileCompactDto> changeFolder(@RequestBody ChangeFolderRequest changeFolderRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File file = fileService.changeFolder(user, changeFolderRequest.getFileId(), changeFolderRequest.getFolderId());
        log.info("파일의 폴더 변경");
        return Response.success(FileCompactDto.of(file));
    }

    @GetMapping("/search")
    public Response<FileFolderResponse> searchFile(@RequestParam String q, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info("파일 검색 검색어: {}", q);
        List<File> files = fileService.searchFile(user, q);
        List<Folder> folders = folderService.searchFile(user, q);
        return Response.success(FileFolderResponse.of(files, folders));
    }

    @GetMapping("/share/{fileId}")
    public ResponseEntity<FileCompactDto> shareFile(@PathVariable String fileId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File file = fileService.generateCode(fileId, user);
        log.info("파일 공유 코드 생성 {}",file.getCode());
        return ResponseEntity.ok().body(FileCompactDto.of(file));
    }

    @GetMapping("/share-stop/{fileId}")
    public Response<String> stopShareFile(@PathVariable String fileId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        fileService.stopShare(fileId, user);
        log.info("파일 공유 중지 {}",fileId);
        return Response.success("파일 공유 중지 완료: " + fileId);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<byte[]> downloadFileCode(@PathVariable String code) {
        File file = fileService.getFileByCode(code);
        log.info("파일 다운로드 코드 {}: 파일: {}", code, file.getId());
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(file.getContentType()))
                .body(file.getFileData());
    }

    @GetMapping("/code-info/{code}")
    public Response<FileCompactDto> getFileInfoCode(@PathVariable String code) {
        File file = fileService.getFileByCode(code);
        log.info("코드로 파일 정보 조회 {}", code);

        return Response.success(FileCompactDto.of(file));
    }

    @PatchMapping("/heart/{fileId}")
    public Response<FileCompactDto> changeHeartState(@PathVariable String fileId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File file = fileService.updateHeartState(user, fileId);
        log.info("파일 하트 변경 {}", fileId);
        return Response.success(FileCompactDto.of(file));
    }

    @GetMapping("/favorite")
    public Response<FileFolderResponse> getAllHearts(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<File> files = fileService.getAllHeartFiles(user);
        List<Folder> folders = folderService.getAllHeartFolders(user);
        log.info("좋아요 표시한 파일 조회 유저: {}", user.getId());
        return Response.success(FileFolderResponse.of(files, folders));
    }

    @GetMapping("/share")
    public Response<FileFolderResponse> getSharFiles(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<File> files = fileService.getAllShareFiles(user);
        log.info("공유 파일리스트 조회: {}", user.getId());
        return Response.success(FileFolderResponse.fromFiles(files));
    }


}
