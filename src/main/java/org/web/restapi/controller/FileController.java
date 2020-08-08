package org.web.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.web.restapi.exception.UserNotFoundException;
import org.web.restapi.model.File;
import org.web.restapi.model.User;
import org.web.restapi.service.FileService;
import org.web.restapi.service.UserService;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<List<File>> createFiles(@RequestParam MultipartFile file) throws IOException {
        fileService.addFile(file);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
