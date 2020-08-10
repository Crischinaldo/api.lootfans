package de.lootfans.restapi.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(IAMService.class);

    @Autowired
    MinioClient minioClient;

    @Value("${minio.bucket.name}")
    String bucketName;

    /**
     * Uploads a file to archiveserver. The passed file is streamed to a bucket which contains upload data.
     *
     * @param file A multipart file
     * @throws IOException
     */
    public void uploadFileStream(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();

        try {

            LOGGER.info("Upload file {} to archive.", file.getOriginalFilename());

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            //TODO: Set User Meta data OR Set file ref into user

        } catch (ErrorResponseException
                | InsufficientDataException
                | InternalException
                | InvalidBucketNameException
                | InvalidKeyException
                | InvalidResponseException
                | NoSuchAlgorithmException
                | ServerException
                | XmlParserException e) {

            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Configure Bucket environment by creating bucket if not exist.
     */
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

            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
