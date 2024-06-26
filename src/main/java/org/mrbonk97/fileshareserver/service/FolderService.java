package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.FileShareApplicationException;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.repository.FolderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FolderService {
    private final FolderRepository folderRepository;

    public Folder createFolder(String folderName, User user) {
        Folder folder = new Folder();
        folder.setFolderName(folderName);
        folder.setUser(user);
        return folderRepository.save(folder);
    }

    public Folder createFolder(String folderName, String parentFolderId, User user) {
        Folder parentFolder = folderRepository.findById(parentFolderId).orElseThrow(() -> new FileShareApplicationException(ErrorCode.FOLDER_NOT_FOUND));

        Folder folder = new Folder();
        folder.setFolderName(folderName);
        folder.setUser(user);
        folder.setParentFolder(parentFolder);

        return folderRepository.save(folder);
    }

    @Transactional
    public void deleteFolder(String folderId, User user) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new FileShareApplicationException(ErrorCode.FOLDER_NOT_FOUND));
        if(!folder.getUser().equals(user)) throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);
        folderRepository.DeleteFolderRecursive1(folderId);
        folderRepository.DeleteFolderRecursive2(folderId);
    }

    public Folder loadById(String folderId) {
        return folderRepository.findById(folderId).orElseThrow(() -> new FileShareApplicationException(ErrorCode.FOLDER_NOT_FOUND));
    }

    public List<Folder> getChildren(String folderId) {
        return folderRepository.findAllByParentFolderId(folderId);
    }

    public List<Folder> getAllFoldersInHome(User user) {
        return folderRepository.findAllByUserAndParentFolderIsNull(user);
    }

    public Folder changeFolder(String folderId, String parentFolderId, User user) {
        if(Objects.equals(folderId, parentFolderId)) return null;

        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new FileShareApplicationException(ErrorCode.FOLDER_NOT_FOUND));
        Folder parentFolder = folderRepository.findById(parentFolderId).orElseThrow(() -> new FileShareApplicationException(ErrorCode.FOLDER_NOT_FOUND));


        if(!folder.getUser().equals(user)) throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);
        if(!parentFolder.getUser().equals(user)) throw new FileShareApplicationException(ErrorCode.INVALID_PERMISSION);

        folder.setParentFolder(parentFolder);
        return folderRepository.save(folder);
    }



    public List<Map<String,String>> getFolderDepth(String folderId) {
        return folderRepository.findFolderDepth(folderId);
    }

    public List<Folder> getAllHeartFolders(User user) {
        return folderRepository.findAllByUserAndHeart(user, true);
    }

    public Folder updateHeartState(String folderId) {
        Folder folder = loadById(folderId);
        folder.setHeart(!folder.getHeart());
        return folderRepository.save(folder);
    }

    public List<Folder> searchFile(User user, String q) {
        return folderRepository.findAllByUserAndFolderNameContainingIgnoreCase(user, q);
    }
}
