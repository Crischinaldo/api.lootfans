package de.lootfans.restapi.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "filemetadata_id", referencedColumnName = "id")
    private FileMetaData fileMetaData;

    public File() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public FileMetaData getFileMetaData() {
        return fileMetaData;
    }

    public void setFileMetaData(FileMetaData fileMetaData) {
        this.fileMetaData = fileMetaData;
    }
}
