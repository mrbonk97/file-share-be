package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String folderName;
    @ManyToOne
    private Folder parentFolder;
    @OneToOne
    User user;
}
