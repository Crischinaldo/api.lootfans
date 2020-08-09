package de.lootfans.restapi.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class ArchiveService {

    @Autowired
    MinioClient minioClient;

    @Value("${minio.bucket.name}")
    String bucketName;

    public void setUp() {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build());
            }


        } catch (RegionConflictException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | ErrorResponseException
                | InvalidResponseException
                | InvalidBucketNameException
                | ServerException
                | InsufficientDataException
                | XmlParserException
                | InternalException
                | IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadFileStream(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (ErrorResponseException
                | InsufficientDataException
                | InternalException
                | InvalidBucketNameException
                | InvalidKeyException
                | InvalidResponseException
                | NoSuchAlgorithmException
                | ServerException
                | XmlParserException e) {
            e.printStackTrace();
        }

    }

}
