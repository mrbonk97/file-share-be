package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class FileData {
    @Id
    private String hashedFileName;
    private String originalFileName;
    private String contentType;
    private Long size;

    @ManyToOne
    private Account account;
    private Date createdAt;
    private Date scheduledDeleteDate;

    @Transient
    byte [] bytes;

}
