package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.FileData;
import org.mrbonk97.fileshareserver.repository.StorageRepository;
import org.mrbonk97.fileshareserver.utils.ImageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DatabaseStorageService {
    private final StorageRepository storageRepository;

    public FileData uploadFile(MultipartFile multipartFile, Account account) throws IOException {
        FileData fileData = new FileData();
        fileData.setAccount(account);
        fileData.setContentType(multipartFile.getContentType());
        fileData.setFileData(ImageUtils.compressImage(multipartFile.getBytes()));
        fileData.setSize(multipartFile.getSize());
        fileData.setOriginalFileName(multipartFile.getOriginalFilename());
        fileData.setContentType(multipartFile.getContentType());
        return storageRepository.save(fileData);
    }

    public FileData downloadFile(String id, Account account) {
        FileData fileData = storageRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        if(!account.equals(fileData.getAccount())) throw new RuntimeException("Not Matched");
        fileData.setDecompressedData(ImageUtils.decompresImage(fileData.getFileData()));
        return fileData;
    }

    public void deleteFile(String id, Account account) {
        FileData fileData = storageRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        if(!account.equals(fileData.getAccount())) throw new RuntimeException("Not Matched");
        storageRepository.delete(fileData);
    }

    public Page<FileData> getFiles (Pageable pageable, Account account) {
        return storageRepository.findByAccountOrderByCreatedAtDesc(account, pageable);
//        return storageRepository.findByAccountOrderByCreatedAtDesc(account, pageable).map((item) -> {
//            FileData fileData = new FileData();
//            fileData.setHashedFileName(item.getHashedFileName());
//            fileData.setOriginalFileName(item.getOriginalFileName());
//            fileData.setContentType(item.getContentType());
//            fileData.setSize(item.getSize());
//            fileData.setFileData(item.getFileData());
//            fileData.setCreatedAt(item.getCreatedAt());
//            fileData.setUpdatedAt(item.getUpdatedAt());
//            fileData.setScheduledDeleteDate(item.getScheduledDeleteDate());
//            return fileData;
//        });
    }


    public FileData downloadFile2(String id) {
        FileData fileData = storageRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        fileData.setDecompressedData(ImageUtils.decompresImage(fileData.getFileData()));
        return fileData;
    }
}
