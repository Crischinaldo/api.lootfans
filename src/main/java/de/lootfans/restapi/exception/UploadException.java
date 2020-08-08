package de.lootfans.restapi.exception;

import java.io.IOException;

public class UploadException extends IOException {

    public UploadException(String message) {
        super(message);
    }
}
