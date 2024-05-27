package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.controller.response.FileListResponse;
import org.mrbonk97.fileshareserver.controller.response.UploadFileResponse;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.service.StorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/api/files")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam MultipartFile file, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        File file2 = storageService.uploadFile(file, user);
        log.info("유저: {} 파일 저장. 파일: {}",user.getId(), file2.getHashedFileName());
        return ResponseEntity.ok().body(UploadFileResponse.of(file2));
    }

    @GetMapping("/preview/{filename}")
    public ResponseEntity<byte[]> preview(@PathVariable String filename, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File file = storageService.downloadFile(filename, user);
        log.info("유저: {} 파일 미리보기. 파일: {}",user.getId(), file.getHashedFileName());
        return ResponseEntity.ok().contentType(MediaType.valueOf(file.getContentType())).body(file.getDecompressedData());
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File file = storageService.downloadFile(filename, user);
        log.info("유저: {} 파일 다운로드. 파일: {}",user.getId(), file.getHashedFileName());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", file.getOriginalFileName()))
                .contentType(MediaType.valueOf(file.getContentType()))
                .body(file.getDecompressedData());
    }

    @GetMapping
    public ResponseEntity<FileListResponse> getFiles(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<File> files = storageService.getFiles(user);
        log.info("유저: {} 파일 목록 조회",user.getId());
        return ResponseEntity.ok().body(FileListResponse.of(files));
    }

}
