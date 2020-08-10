package de.lootfans.restapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains all exception classes and functions as controller advice class in this application.
 * Defines a handleException method which will handle all exceptions declared in it and delegates
 * the exception to a specific handler method. A handler method exists for each exception eg.
 * a UserNotFoundException is handled by a handleUserNotFoundException method.
 * A handler method contains the logic to treat a given exception. Afterwards a method
 * is called to send the resulting response.
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    /**
     * Provides handling for exceptions throughout this api.
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvisor.class);

    /**
     *
     * @param ex exception to be handled
     * @param request corresponding request the exception occured
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ApiError> handleControllerException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException unfe = (UserNotFoundException) ex;

            return handleUserNotFoundException(unfe, headers, status, request);
        }

        ApiError apiError = new ApiError(new Date(), ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * customize response for UserNotFoundException
     *
     * @param ex The exception
     * @param headers The headers of the response
     * @param status The http status code of the response
     * @param request The request to respond to
     * @return a {@code ResponseEntity} instance
     */
        protected ResponseEntity<ApiError> handleUserNotFoundException (UserNotFoundException ex, HttpHeaders
        headers, HttpStatus status, WebRequest request){

            ApiError apiError = new ApiError(new Date(), ex.getMessage());

            return new ResponseEntity<>(apiError, status);
        }

}
