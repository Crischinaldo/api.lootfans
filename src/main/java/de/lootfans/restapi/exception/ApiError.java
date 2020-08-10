package de.lootfans.restapi.exception;

import java.util.Date;
import java.util.List;

public class ApiError {
    private Date timestamp;
    private String error;

    public ApiError(Date timestamp, String error) {
        this.timestamp = timestamp;
        this.error = error;
    }

    public ApiError(Date timestamp) {
        this.timestamp = timestamp;
    }
}

