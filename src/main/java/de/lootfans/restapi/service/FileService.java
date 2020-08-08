package de.lootfans.restapi.service;

import de.lootfans.restapi.model.FileMetaData;
import de.lootfans.restapi.repository.FileMetaDataRepository;
import de.lootfans.restapi.repository.FileRepository;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import de.lootfans.restapi.model.File;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileMetaDataRepository fileMetaDataRepository;

    @Autowired
    MinioClient minioClient;


    public List<File> getFiles(){
        return fileRepository.findAll();
    }

    public File addFile(@RequestBody MultipartFile file) throws IOException {

        InputStream inputStream = null;

        // https://docs.min.io/docs/java-client-api-reference.html

        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("test-bucket").build());
            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket("test-bucket")
                                .build());
            }

            inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket("test-bucket")
                    .object(file.getOriginalFilename())
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
                | InsufficientDataException
                | RegionConflictException e) {
            e.printStackTrace();
        } finally {
            assert inputStream != null;
            inputStream.close();
        }


        File _file = new File();

        FileMetaData fileMetaData = new FileMetaData(file.getOriginalFilename(), file.getContentType(), file.getSize());

        _file.setFileMetaData(fileMetaData);

        return fileRepository.save(_file);
    }
}
