package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;

    @GetMapping("/")
    public void uploadFile(MultipartFile multipartFile) {
        File file = new File();
        System.out.println(multipartFile.getSize());
        System.out.println(multipartFile.getName());
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getOriginalFilename());
        System.out.println(multipartFile.getResource());

    }
}
