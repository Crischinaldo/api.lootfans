package org.web.restapi.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "filemetadata")
public class FileMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    @NotNull(message = "File name needs to be provided")
    private String fileName;

    @Column(name = "contenttype")
    private String contentType;

    @Column(name = "file_size")
    @Max(value = 10240000, message = "Bytes should not be greater than 10240000")
    private long fileSize;

    @OneToOne(mappedBy = "filemetadata")
    private FileMetaData fileMetaData;

    public FileMetaData() {}

    public FileMetaData(String fileName, String contentType, long fileSize) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public FileMetaData getFileMetaData() {
        return fileMetaData;
    }

    public void setFileMetaData(FileMetaData fileMetaData) {
        this.fileMetaData = fileMetaData;
    }
}
