package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User implements OAuth2User, UserDetails {
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

    @Override
    public boolean isAccountNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return deletedAt == null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.toString()));
    }

    @Override
    public String getName() {
        return username;
    }
}
