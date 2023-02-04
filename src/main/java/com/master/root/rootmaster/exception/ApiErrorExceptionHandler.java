package com.master.root.rootmaster.exception;

import com.master.root.rootmaster.exception.error.ApiError;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import static org.springframework.boot.web.servlet.support.ErrorPageFilter.ERROR_REQUEST_URI;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;

@Slf4j
@RestControllerAdvice
public final class ApiErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(BadRequestException ex, WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return handleConstraintViolation((ConstraintViolationException) ex.getCause(), request);
        }
        return buildResponseEntity(buildApiError(BAD_REQUEST, ex, request));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ValidationException ex, WebRequest request) {
        var apiError = buildApiError(BAD_REQUEST, "Validation Error: " + ex.getMessage(), request);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MaxUploadSizeExceededException.class
    })
    public ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request) {
        return buildResponseEntity(buildApiError(BAD_REQUEST, ex, request));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalState(IllegalStateException ex, WebRequest request) {
        return buildResponseEntity(buildApiError(CONFLICT, ex, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return buildResponseEntity(buildApiError(INTERNAL_SERVER_ERROR, ex, request));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    private ApiError buildApiError(@Nullable HttpStatus status, String message, WebRequest request) {
        log.error(message);
        return ApiError.from(status != null ? status : INTERNAL_SERVER_ERROR, message, getUrlPath(request));
    }

    private ApiError buildApiError(HttpStatus status, Exception ex, WebRequest request) {
        log.error("Exception occurred", ex);
        return ApiError.from(status, extractMessage(ex), getUrlPath(request));
    }

    @Nullable
    private String getUrlPath(WebRequest request) {
        var pathFromHandler = (String) request.getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, SCOPE_REQUEST);
        var pathFromServlet = (String) request.getAttribute(ERROR_REQUEST_URI, SCOPE_REQUEST);
        return StringUtils.isNotBlank(pathFromServlet) ? pathFromServlet : pathFromHandler;
    }

    private static String extractMessage(final Throwable exception) {
        if (exception.getCause() == null) {
            return exception.getMessage();
        } else {
            return extractMessage(exception.getCause());
        }
    }
}
