//package org.mrbonk97.fileshareserver.service;
//
//import lombok.RequiredArgsConstructor;
//import org.mrbonk97.fileshareserver.exception.ErrorCode;
//import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
//import org.mrbonk97.fileshareserver.model.User;
//import org.mrbonk97.fileshareserver.model.FileData;
//import org.mrbonk97.fileshareserver.repository.StorageRepository;
//import org.mrbonk97.fileshareserver.utils.ImageUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Service
//public class DatabaseStorageService {
//    private final StorageRepository storageRepository;
//
//    public FileData loadByFileId(String id) {
//        return storageRepository.findById(id).orElseThrow(() -> new FileShareApplicationException(ErrorCode.FILE_NOT_FOUND));
//    }
//
//    public FileData uploadFile(MultipartFile multipartFile, User user) throws IOException {
//        FileData fileData = new FileData();
//        fileData.setUser(user);
//        fileData.setContentType(multipartFile.getContentType());
//        fileData.setFileData(ImageUtils.compressImage(multipartFile.getBytes()));
//        fileData.setSize(multipartFile.getSize());
//        fileData.setOriginalFileName(multipartFile.getOriginalFilename());
//        fileData.setContentType(multipartFile.getContentType());
//        return storageRepository.save(fileData);
//    }
//
//    public FileData downloadFile(String id, User user) {
//        FileData fileData = loadByFileId(id);
//        if(!user.equals(fileData.getUser())) throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);
//        fileData.setDecompressedData(ImageUtils.decompresImage(fileData.getFileData()));
//        return fileData;
//    }
//
//    public void deleteFile(String id, User user) {
//        FileData fileData = loadByFileId(id);
//        if(!user.equals(fileData.getUser())) throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);
//        storageRepository.delete(fileData);
//    }
//
//    public FileData downloadFile2(String id) {
//        FileData fileData = loadByFileId(id);
//        fileData.setDecompressedData(ImageUtils.decompresImage(fileData.getFileData()));
//        return fileData;
//    }
//}
