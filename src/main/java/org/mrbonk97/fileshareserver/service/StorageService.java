package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.repository.StorageRepository;
import org.mrbonk97.fileshareserver.utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StorageService {
    private final StorageRepository storageRepository;
    private final FolderService folderService;

    public File loadByFileId(String id) {
        return storageRepository.findById(id).orElseThrow(() -> new RuntimeException("으악스"));
    }

    public File uploadFile(MultipartFile multipartFile, String folderId, User user) throws IOException {
        File file = new File();
        file.setUser(user);
        file.setContentType(multipartFile.getContentType());
        file.setFileData(ImageUtils.compressImage(multipartFile.getBytes()));
        file.setSize(multipartFile.getSize());
        file.setOriginalFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());

        if(folderId != null) {
            Folder folder = folderService.loadById(folderId);
            file.setFolder(folder);
        };

        return storageRepository.save(file);
    }

    public File downloadFile(String id, User user) {
        File file = loadByFileId(id);
        if(!user.equals(file.getUser())) throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);
        file.setDecompressedData(ImageUtils.decompresImage(file.getFileData()));
        return file;
    }

    public void deleteFile(String id, User user) {
        File file = loadByFileId(id);
        if(!user.equals(file.getUser())) throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);
        storageRepository.delete(file);
    }

    public List<File> getFiles(User user) {
        return storageRepository.findAllByUser(user);
    }

    public List<File> getFilesByFolder(Folder folder) {
        return storageRepository.findAllByFolder(folder);
    }

    public void changeFolder(String fileId, String folderId) {
        File file = loadByFileId(fileId);
        Folder folder = folderService.loadById(folderId);
        file.setFolder(folder);
        storageRepository.save(file);
    }

    public List<File> searchFile(String filename) {
        return null;
    }

    public String generateCode(String fileId, User user) {
        File file = loadByFileId(fileId);
        if(file.getCode() != null)
            return file.getCode();

        if(!user.equals(file.getUser())) throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);

        UUID uuid = UUID.randomUUID();
        String code = uuid.toString().substring(0,8);
        file.setCode(code);
        return code;
    }
}