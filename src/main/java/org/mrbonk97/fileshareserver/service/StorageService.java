package org.mrbonk97.fileshareserver.service;

import org.mrbonk97.fileshareserver.model.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;


public interface StorageService {
    FileData store(MultipartFile multipartFile);
    Stream<Path> loadAll();
    Path load(String fileName);
    FileData loadAsResource(String fileName);
    void deleteAll();
}
