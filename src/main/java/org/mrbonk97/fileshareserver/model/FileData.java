package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate scheduledDeleteDate;

    @PrePersist
    void create() {
        this.createdAt = LocalDateTime.now();
        this.scheduledDeleteDate = LocalDate.now().plusWeeks(1);
    }

    @PreUpdate
    void update() {
        this.updatedAt = LocalDateTime.now();
    }


}
