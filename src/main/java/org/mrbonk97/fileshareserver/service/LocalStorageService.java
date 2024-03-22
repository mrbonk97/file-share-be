package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.exception.StorageException;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.FileData;
import org.mrbonk97.fileshareserver.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Primary
@Qualifier("local")
@RequiredArgsConstructor
@Service
public class LocalStorageService implements StorageService{
    private final FileDataRepository fileDataRepository;
    @Value("${localstorage.root-location}")
    private String rootLocation;

    @Override
    public FileData store(Account account, MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) throw new StorageException("파일이 비어있습니다.");

        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        int lastDotIdx = originalFilename.lastIndexOf(".");

        String randomFileName = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = originalFilename.substring(lastDotIdx);
        Path destination = Path.of(rootLocation + "/" + randomFileName + extension).toAbsolutePath();

        if(!destination.getParent().equals(Path.of(rootLocation).toAbsolutePath()))
            throw new StorageException("Cannot store file outside current directory.");


        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

            FileData fileData = new FileData();
            fileData.setHashedFileName(randomFileName + extension);
            fileData.setContentType(multipartFile.getContentType());
            fileData.setSize(multipartFile.getSize());
            fileData.setOriginalFileName(multipartFile.getOriginalFilename());
            fileData.setAccount(account);
            return fileDataRepository.save(fileData);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String fileName) {
        return null;
    }

    @Override
    public FileData loadAsResource(Account account, String fileName) {
        FileData fileData = fileDataRepository.findById(fileName).orElseThrow(() -> new RuntimeException("asd"));
        if(!fileData.getAccount().equals(account)) throw new RuntimeException("권한이 없음");
        String filePath = rootLocation + "/" +  fileName;

        try{
            byte [] bytes = Files.readAllBytes(new File(filePath).toPath());
            fileData.setBytes(bytes);
            return fileData;

        } catch (IOException e) {
            System.out.println("파일을 찾을 수 없습니다. " + e.getMessage());
        }
        return null;

    }

    @Override
    public void deleteAll() {

    }
}
