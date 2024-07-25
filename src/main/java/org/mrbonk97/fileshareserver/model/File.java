package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String originalFileName;
    private String contentType;
    private Long size;
    private String code;
    private Boolean heart = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne private Folder folder;
    @ManyToOne private User user;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB") // 16mb 까지 저장 가능
    private byte [] fileData;

    @PrePersist
    void create() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void update() {
        this.updatedAt = LocalDateTime.now();
    }

}
