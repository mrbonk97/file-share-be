package org.mrbonk97.fileshareserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Token"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs");

    private final HttpStatus httpStatus;
    private final String message;
}
