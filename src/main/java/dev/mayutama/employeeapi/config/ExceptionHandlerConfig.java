package dev.mayutama.employeeapi.config;

import dev.mayutama.employeeapi.exception.ApplicationException;
import dev.mayutama.employeeapi.model.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerConfig {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(
            ApplicationException applicationException,
            HttpServletRequest request
    ) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(applicationException.getErrorCode())
                .message(applicationException.getMessage())
                .statusName(applicationException.getHttpStatus().name())
                .statusCode(applicationException.getHttpStatus().value())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(errorResponse, applicationException.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception,
            HttpServletRequest req
    ) {
        Throwable rootCause = exception.getRootCause();
        String message = rootCause != null ? rootCause.getMessage() : exception.getMessage();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("-")
                .message("Duplicate or Invalid Data: " + message)
                .statusName(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(req.getRequestURI())
                .method(req.getMethod())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUniversalException(
            Exception exception,
            HttpServletRequest req
    ) {
        System.out.println("TEs error");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode("-")
                .message(exception.getMessage())
                .statusName(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(req.getRequestURI())
                .method(req.getMethod())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
