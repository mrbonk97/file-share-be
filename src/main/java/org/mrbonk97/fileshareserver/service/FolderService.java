package org.mrbonk97.fileshareserver.service;

import lombok.RequiredArgsConstructor;
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
        Folder parentFolder = folderRepository.findById(parentFolderId).orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없음"));

        Folder folder = new Folder();
        folder.setFolderName(folderName);
        folder.setUser(user);
        folder.setParentFolder(parentFolder);

        return folderRepository.save(folder);
    }

    @Transactional
    public void deleteFolder(String folderId, User user) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));
        if(!folder.getUser().equals(user)) throw new RuntimeException("폴더 삭제 권한이 없습니다.");
        System.out.println("삭제 시작");
        folderRepository.DeleteFolderRecursive1(folderId);
        System.out.println("삭제 시작1");
        folderRepository.DeleteFolderRecursive2(folderId);
        System.out.println("삭제 시작2");
    }

    public Folder loadById(String folderId) {
        return folderRepository.findById(folderId).orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));
    }

    public List<Folder> getChildren(String folderId) {
        return folderRepository.findAllByParentFolderId(folderId);
    }

    public void changeFolder(String folderId, String parentFolderId, User user) {
        if(Objects.equals(folderId, parentFolderId)) return;

        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));
        Folder parentFolder = folderRepository.findById(parentFolderId).orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));


        if(!folder.getUser().equals(user)) throw new RuntimeException("권한 없음");
        if(!parentFolder.getUser().equals(user)) throw new RuntimeException("권한 없음");

        folder.setParentFolder(parentFolder);
        folderRepository.save(folder);
    }



    public List<Map<String,String>> getFolderDepth(String folderId) {
        return folderRepository.findFolderDepth(folderId);
    }

    public List<Folder> getAllHeartFolders(User user) {
        return folderRepository.findAllByUserAndHeart(user, true);
    }
}
