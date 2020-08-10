package de.lootfans.restapi.service;

import de.lootfans.restapi.model.FileMetaData;
import de.lootfans.restapi.repository.FileMetaDataRepository;
import de.lootfans.restapi.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import de.lootfans.restapi.model.File;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileMetaDataRepository fileMetaDataRepository;

    @Autowired
    ArchiveService archiveService;


    public List<File> getFiles(){
        return fileRepository.findAll();
    }

    public File addFile(@RequestBody MultipartFile file) {

        try {
            archiveService.uploadFileStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File fileEntity = new File();

        FileMetaData fileMetaData = new FileMetaData(file.getOriginalFilename(), file.getContentType(), file.getSize());

        fileEntity.setFileMetaData(fileMetaData);

        return fileRepository.save(fileEntity);
    }
}
