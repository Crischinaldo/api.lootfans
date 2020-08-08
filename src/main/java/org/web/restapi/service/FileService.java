package org.web.restapi.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.web.restapi.exception.UploadException;
import org.web.restapi.model.File;
import org.web.restapi.model.FileMetaData;
import org.web.restapi.repository.FileMetaDataRepository;
import org.web.restapi.repository.FileRepository;
import org.web.restapi.specification.FileSpecifications;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileMetaDataRepository fileMetaDataRepository;

    @Autowired
    MinioClient minioClient


    public List<File> getFiles(){
        return fileRepository.findAll();
    }

    public File addFile(@RequestBody MultipartFile file) throws IOException {

        InputStream inputStream = null;

        try {

            inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket("test-bucket")
                    .object(file.getName())
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

        } catch (InvalidKeyException
                | NoSuchAlgorithmException
                | ErrorResponseException
                | InvalidResponseException
                | ServerException
                | InvalidBucketNameException
                | XmlParserException
                | InternalException
                | InsufficientDataException e) {
            e.printStackTrace();
        } finally {
            assert inputStream != null;
            inputStream.close();
        }


        File _file = new File();

        FileMetaData fileMetaData = new FileMetaData(file.getName(), file.getContentType(), file.getSize();

        _file.setFileMetaData(fileMetaData);

        return fileRepository.save(_file);
    }
}
