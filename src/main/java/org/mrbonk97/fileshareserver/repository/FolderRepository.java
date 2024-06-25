package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.dto.FolderDepthDto;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface FolderRepository extends JpaRepository<Folder, String> {
    List<Folder> findAllByParentFolderId(String folderId);

    @Query(nativeQuery = true, value = "with recursive t3(folder_name, id, parent_folder_id) as (\n" +
            "    select t1.folder_name, t1.id, t1.parent_folder_id from folder as t1 where t1.id = :fileId \n" +
            "\n" +
            "    union all\n" +
            "\n" +
            "    select t2.folder_name, t2.id, t2.parent_folder_id from folder as t2 inner join t3 on t2.id = t3.parent_folder_id\n" +
            ") select folder_name, id from t3;")
    List<Map<String,String>> findFolderDepth(String fileId);

    @Modifying
    @Query(nativeQuery = true, value= "with recursive t3(folder_name, id, parent_folder_id) as (\n" +
            "select t1.folder_name, t1.id, t1.parent_folder_id from folder as t1 where t1.id = :parentFolderId  \n" +
            "\tunion all\n" +
            "select t2.folder_name, t2.id, t2.parent_folder_id from folder as t2 inner join t3 on t2.parent_folder_id = t3.id)\n" +
            "delete from file where folder_id in (select id from t3)")
    void DeleteFolderRecursive1(String parentFolderId);

    @Modifying
    @Query(nativeQuery = true, value = "" +
            "with recursive t3(folder_name, id, parent_folder_id) as " +
            "(" +
            " select t1.folder_name, t1.id, t1.parent_folder_id from folder as t1 where t1.id = :parentFolderId " +
            " union all" +
            " select t2.folder_name, t2.id, t2.parent_folder_id from folder as t2 inner join t3 on t2.parent_folder_id = t3.id" +
            ") " +
            " delete from folder where id in (select id from t3) ")
    void DeleteFolderRecursive2(String parentFolderId);

    List<Folder> findAllByUserAndHeart(User user, Boolean heart);

    List<Folder> findAllByUserAndParentFolderIsNull(User user);



}

