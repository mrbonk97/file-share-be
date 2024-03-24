package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE account SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
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
    private LocalDateTime emailAuthenticated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    @Transient
    private String accessToken;
    @Transient
    private String refreshToken;

    @PrePersist
    void create() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void update() {
        this.updatedAt = LocalDateTime.now();
    }

}
