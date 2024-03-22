package org.mrbonk97.fileshareserver.dto.fileData.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.model.FileData;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class FileListResponse {

    private static class Files {
        private String hashedFileName;
        private String originalFileName;
        private String contentType;
        private Long size;
        private byte [] fileData;
        private Date createdAt;
        private Date updatedAt;
        private Date scheduledDeleteDate;
    }

    List<Files> files = new ArrayList<>();




}
