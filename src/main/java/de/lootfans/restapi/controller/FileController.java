package de.lootfans.restapi.controller;

import de.lootfans.restapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import de.lootfans.restapi.model.File;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/files"
    )
    public ResponseEntity<File> createFiles(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.addFile(file);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // TODO: Customize // LIST OF FILES or SINGLE FILE?
    }

}
