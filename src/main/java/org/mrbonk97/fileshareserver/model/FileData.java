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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String hashedFileName;
    private String originalFileName;
    private String contentType;
    private Long size;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB") // 16mb 까지 저장 가능
    private byte [] fileData;

    @ManyToOne
    private Account account;

    @Transient
    private byte [] decompressedData;
    private Date createdAt;
    private Date updatedAt;
    private Date scheduledDeleteDate;

    @PrePersist
    void create() {
        this.createdAt = new Date();
        this.scheduledDeleteDate = new Date(System.currentTimeMillis() + 604800000L);
    }

    @PreUpdate
    void update() {
        this.updatedAt = new Date();
    }


}
