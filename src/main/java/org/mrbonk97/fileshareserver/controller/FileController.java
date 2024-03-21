package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.dto.Response;
import org.mrbonk97.fileshareserver.dto.fileData.request.UploadFileRequest;
import org.mrbonk97.fileshareserver.dto.fileData.response.UploadFileResponse;
import org.mrbonk97.fileshareserver.model.FileData;
import org.mrbonk97.fileshareserver.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/files")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final StorageService storageService;
    @PostMapping
    public Response<UploadFileResponse> uploadFile(@ModelAttribute UploadFileRequest uploadFileRequest) {
        FileData fileData = storageService.store(uploadFileRequest.getFile());
        return Response.success(UploadFileResponse.of(fileData));
    }

    @GetMapping("/{filename}")
    public ResponseEntity<?> serveFile(@PathVariable String filename) {
        FileData fileData = storageService.loadAsResource(filename);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(fileData.getContentType()))
                .body(fileData.getBytes());
    }

    @GetMapping
    public String test() {
        return "file api test";
    }


}
