package de.lootfans.restapi.controller;

import de.lootfans.restapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import de.lootfans.restapi.model.File;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @RolesAllowed("user")
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/files"
    )
    public ResponseEntity<File> createFiles(@RequestHeader(value="Authorization") String bearerToken,
                                            @RequestParam("file") MultipartFile file) throws IOException {

        fileService.addFile(bearerToken, file); // TODO: LIST OF FILES or SINGLE FILE?

        return new ResponseEntity<>(HttpStatus.CREATED); // TODO: Customize
    }

}
