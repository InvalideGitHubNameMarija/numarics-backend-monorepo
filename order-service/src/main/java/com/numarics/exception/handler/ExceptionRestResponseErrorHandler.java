package com.numarics.exception.handler;

import com.numarics.exception.ErrorResponse;
import com.numarics.exception.ServiceUnavailableException;
import feign.FeignException.FeignClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionRestResponseErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.error("MethodArgumentNotValidException: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(status, "Validation Error");
        errorResponse.addValidationErrors(e.getBindingResult().getFieldErrors());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.error("NotFoundException: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(status, e.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(NotFoundException e) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        log.error("ServiceUnavailableException: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(status, e.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeExceptions(RuntimeException e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("Unexpected RuntimeException: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(status, "Internal Server Error");
        return new ResponseEntity<>(errorResponse, status);
    }
}
