package org.mrbonk97.fileshareserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class File {
    @Id
    private Long id;
    private Long size;
    private String originalFileName;
    private String contentType;

    @ManyToOne
    private Account account;
    private Date createdAt;
    private Date scheduledDeleteDate;


}
