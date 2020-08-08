package de.lootfans.restapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

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

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ApiError> handleControllerException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException unfe = (UserNotFoundException) ex;

            return handleUserNotFoundException(unfe, headers, status, request);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
            List<String> errors = Collections.singletonList(ex.getMessage());
            status = HttpStatus.NOT_FOUND;
            return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
        }

    /**
     *
     *  A single place to customize the response body of all Exception types.
     *
     * @param ex The exception
     * @param body The body of the response
     * @param headers The headers of the response
     * @param status The http status code of the response
     * @param request The request to respond to
     * @return a {@code ResponseEntity} instance
     */
        protected ResponseEntity<ApiError> handleExceptionInternal (Exception ex, ApiError body, HttpHeaders
        headers, HttpStatus status, WebRequest request){
            if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
                request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
            }

            return new ResponseEntity<>(body, headers, status);
        }
}
