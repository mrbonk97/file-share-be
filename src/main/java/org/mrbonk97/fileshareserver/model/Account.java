package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;
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
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void update() {
        this.updatedAt = new Date();
    }

}
