package org.mrbonk97.fileshareserver.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import static org.mrbonk97.fileshareserver.exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileShareApplicationException.class)
    public ResponseEntity<?> errorHandler(FileShareApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorCode().getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> errorHandler2(Exception e) {
//        System.out.println("응애 응애1" + e.hashCode());
//        System.out.println("응애 응애2" + e.getMessage());
//        return ResponseEntity.status(e.hashCode()).body(e.getMessage());
//    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> errorHandler3(ExpiredJwtException e) {
        System.out.println("응애 응애1" + e.hashCode());
        System.out.println("응애 응애2" + e.getMessage());
        System.out.println("응애 응애3" + e.getClaims());
        System.out.println("응애 응애4" + e.toString());
        return ResponseEntity.status(e.hashCode()).body(e.getMessage());
    }

//    @ExceptionHandler(FileShareApplicationException.class)
//    public ResponseEntity<?> errorHandler(FileShareApplicationException e) {
//        log.error("에러 발생 {}", e.toString());
//        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
//                .body(Response.error(e.getErrorCode().name()));
//    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<?> databaseErrorHandler(IllegalArgumentException e) {
//        log.error("에러 발생 {}", e.toString());
//        return ResponseEntity.status(DATABASE_ERROR.getHttpStatus())
//                .body(Response.error(DATABASE_ERROR.name()));
//    }
}