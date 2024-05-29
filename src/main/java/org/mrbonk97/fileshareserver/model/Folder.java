package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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
    @ManyToOne
    User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(id, folder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
