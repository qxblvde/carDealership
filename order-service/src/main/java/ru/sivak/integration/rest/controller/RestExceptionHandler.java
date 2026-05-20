package ru.sivak.integration.rest.controller;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sivak.domain.exceptions.DomainException;
import ru.sivak.integration.rest.dto.ApiErrorResponse;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomain(DomainException exception) {
        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), Instant.now())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
        HttpStatus status = isNotFound(exception) ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(
                new ApiErrorResponse(exception.getMessage(), status.value(), Instant.now())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        String message = "Validation failed";
        FieldError fieldError = exception.getBindingResult().getFieldError();
        if (fieldError != null && fieldError.getDefaultMessage() != null) {
            message = fieldError.getDefaultMessage();
        }
        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(message, HttpStatus.BAD_REQUEST.value(), Instant.now())
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiErrorResponse("Access denied", HttpStatus.FORBIDDEN.value(), Instant.now())
        );
    }

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleGrpc(StatusRuntimeException exception) {
        HttpStatus status = mapGrpcStatus(exception.getStatus().getCode());
        return ResponseEntity.status(status).body(
                new ApiErrorResponse(resolveGrpcMessage(exception), status.value(), Instant.now())
        );
    }

    private boolean isNotFound(IllegalArgumentException exception) {
        String message = exception.getMessage();
        return message != null && message.toLowerCase().contains("not found");
    }

    private HttpStatus mapGrpcStatus(Status.Code code) {
        return switch (code) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.SERVICE_UNAVAILABLE;
        };
    }

    private String resolveGrpcMessage(StatusRuntimeException exception) {
        String description = exception.getStatus().getDescription();
        return description == null ? "StorageService is unavailable" : description;
    }
}
