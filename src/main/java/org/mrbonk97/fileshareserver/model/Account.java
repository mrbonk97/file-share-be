package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;
    private String imageUrl;
    @OneToMany
    private List<FileData> fileDataList;
    @Enumerated(EnumType.STRING)
    private Provider provider = Provider.local;
    private String providerId;
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.role_user;
    private Date emailAuthenticated;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    @PrePersist
    void create() {
        this.createdAt = new Date();
    }

    @PreUpdate
    void update() {
        this.updatedAt = new Date();
    }

}
