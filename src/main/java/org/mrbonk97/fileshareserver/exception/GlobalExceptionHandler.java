package org.mrbonk97.fileshareserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.mrbonk97.fileshareserver.exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileShareApplicationException.class)
    public ResponseEntity<?> errorHandler(FileShareApplicationException e) {
        log.error("에러 발생 {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.error(e.getErrorCode().name()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e) {
        log.error("에러 발생 {}", e.toString());
        return ResponseEntity.status(DATABASE_ERROR.getHttpStatus())
                .body(Response.error(DATABASE_ERROR.name()));
    }
}