package de.lootfans.restapi.service;

import com.auth0.jwt.interfaces.Claim;
import de.lootfans.restapi.components.JwtTokenUtil;
import de.lootfans.restapi.model.FileMetaData;
import de.lootfans.restapi.repository.FileMetaDataRepository;
import de.lootfans.restapi.repository.FileRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import de.lootfans.restapi.model.File;

import java.io.IOException;
import java.util.*;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileMetaDataRepository fileMetaDataRepository;

    @Autowired
    ArchiveService archiveService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(IAMService.class);


    public List<File> getFiles(){
        return fileRepository.findAll();
    }

    public File addFile(String bearerToken, @RequestBody MultipartFile file) {

        String token = bearerToken.split(" ")[1];

        LOGGER.info("Token: {}", token);

        Map<String, Claim> claims = jwtTokenUtil.decodeJWT(token);

        String userName = claims.get("preferred_username").asString();

        LOGGER.info("username of token bearer: {}", userName);

        try {
            archiveService.uploadFileStream(file, Collections.singletonMap("username", userName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File fileEntity = new File();

        FileMetaData fileMetaData = new FileMetaData(file.getOriginalFilename(), file.getContentType(), file.getSize());

        fileEntity.setFileMetaData(fileMetaData);

        return fileRepository.save(fileEntity);
    }
}
