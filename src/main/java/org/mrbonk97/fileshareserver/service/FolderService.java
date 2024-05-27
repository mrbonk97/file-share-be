package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.repository.FolderRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FolderService {
    private final FolderRepository folderRepository;

    public void createFolder(String folderName, String parentFolderId, User user) {
        Folder folder = new Folder();
        folder.setFolderName(folderName);
        folder.setUser(user);
        if(parentFolderId != null) {
            Folder parentFolder = folderRepository.findById(parentFolderId).orElseThrow(() -> new RuntimeException("부모 폴더를 찾을 수 없음"));
            folder.setParentFolder(parentFolder);
        }
        folderRepository.save(folder);
    }

    public void deleteFolder(String folderId, User user) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));
        if(folder.getUser() != user) throw new RuntimeException("폴더 삭제 권한이 없습니다.");
        folderRepository.delete(folder);
    }

    public Folder loadById(String folderId) {
        return folderRepository.findById(folderId).orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));

    }
}
