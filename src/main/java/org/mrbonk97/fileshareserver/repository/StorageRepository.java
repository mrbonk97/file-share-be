package org.mrbonk97.fileshareserver.repository;

import org.mrbonk97.fileshareserver.model.File;
import org.mrbonk97.fileshareserver.model.Folder;
import org.mrbonk97.fileshareserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<File, String> {
    List<File> findAllByUser(User user);

    List<File> findAllByFolder(Folder folder);

    void deleteAllByFolder(Folder folder);

    List<File> findAllByOriginalFileNameLike(String fileName);


//    with recursive t3(folder_name, id, parent_folder_id) as (
//    select t1.folder_name, t1.id, t1.parent_folder_id from folder as t1 where t1.id = 'adf3a0b6-2752-423b-b54a-c7d68f57e0b6'
//
//    union all
//
//    select t2.folder_name, t2.id, t2.parent_folder_id from folder as t2 inner join t3 on t2.id = t3.parent_folder_id
//) select * from t3;
    
}
