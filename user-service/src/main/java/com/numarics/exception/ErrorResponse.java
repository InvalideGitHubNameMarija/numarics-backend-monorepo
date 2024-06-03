package com.numarics.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@Getter
@Setter
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int code;

    private HttpStatus status;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ValidationError> validationErrors;

    public ErrorResponse() {
        timestamp = new Date();
    }

    public ErrorResponse(
            HttpStatus httpStatus,
            String message
    ) {
        this();

        this.code = httpStatus.value();
        this.status = httpStatus;
        this.message = message;
        this.validationErrors = new ArrayList<>();
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(error ->
                validationErrors.add(new ValidationError(error.getField(), error.getDefaultMessage()))
        );
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
    }
}
