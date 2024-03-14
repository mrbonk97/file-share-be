package org.mrbonk97.fileshareserver.controller;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/file")
@RequiredArgsConstructor
@RestController
public class FileController {
    private FileService fileService;
    @PostMapping
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getName());
        System.out.println(multipartFile.getResource());
        System.out.println(multipartFile.getOriginalFilename());


//        fileService.uploadFile(multipartFile);
    }
}
