package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.repository.StorageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {
    private final StorageRepository storageRepository;
    private final FolderService folderService;

    public File loadByFileId(String id) {
        return storageRepository.findById(id).orElseThrow(() -> new FileShareApplicationException(ErrorCode.FILE_NOT_FOUND));
    }

    public File uploadFile(MultipartFile multipartFile, String folderId, User user) {
        File file = new File();
        file.setUser(user);
        file.setContentType(multipartFile.getContentType());
        file.setSize(multipartFile.getSize());
        file.setOriginalFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFolder(folderId != null ? folderService.loadById(folderId) : null);

        try{
            file.setFileData(multipartFile.getBytes());
        } catch (IOException e) {
            // TODO: 에러 핸들링
            throw new FileShareApplicationException(ErrorCode.DATABASE_ERROR);
        }

        return storageRepository.save(file);
    }

    public File downloadFile(String id, User user) {
        File file = loadByFileId(id);
        if(!user.equals(file.getUser()))
            throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);
        return file;
    }

    public void deleteFile(String id, User user) {
        File file = loadByFileId(id);
        if(!user.equals(file.getUser()))
            throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);
        storageRepository.delete(file);
    }

    public List<File> getFiles(User user) {
        return storageRepository.findAllByUser(user);
    }

    public List<File> getFilesInHome(User user) {
        return storageRepository.findAllByUserAndFolderIsNull(user);
    }

    public List<File> getFilesByFolder(Folder folder) {
        return storageRepository.findAllByFolder(folder);
    }

    public File changeFolder(User user, String fileId, String folderId) {
        File file = loadByFileId(fileId);
        Folder folder = folderService.loadById(folderId);

        if(!file.getUser().equals(user) || !folder.getUser().equals(user))
            throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);

        file.setFolder(folder);
        return storageRepository.save(file);
    }

    public List<File> searchFile(User user, String filename) {
        return storageRepository.findAllByUserAndOriginalFileNameContainingIgnoreCase(user,  filename);
    }

    public File generateCode(String fileId, User user) {
        File file = loadByFileId(fileId);
        if(file.getCode() != null) return file;

        if(!user.equals(file.getUser()))
            throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);

        file.setCode(UUID.randomUUID().toString().substring(0,8));
        return storageRepository.save(file);
    }

    public File getFileByCode(String code) {
        return storageRepository.findByCode(code).orElseThrow(() -> new FileShareApplicationException(ErrorCode.FILE_NOT_FOUND));
    }

    public void stopShare(String fileId, User user) {
        File file = loadByFileId(fileId);
        if(!user.equals(file.getUser()))
            throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);

        file.setCode(null);
        storageRepository.save(file);
    }


    @Transactional
    public File updateHeartState(User user, String fileId) {
        File file = loadByFileId(fileId);

        if(!user.equals(file.getUser()))
            throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);

        file.setHeart(!file.getHeart());
        return storageRepository.save(file);
    }

    public List<File> getAllHeartFiles(User user) {
        return storageRepository.findAllByUserAndHeart(user, true);
    }

    public List<File> getAllShareFiles(User user) {
        return storageRepository.findAllByUserAndCodeIsNotNull(user);
    }
}