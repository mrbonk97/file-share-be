package org.mrbonk97.fileshareserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid Token"),
    BLOCKED_TOKEN(HttpStatus.FORBIDDEN, "Blocked Token"),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "Expired Token"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "File not found"),
    FOLDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Folder not found"),
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "Url not found"),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED,"Refresh token is empty" ),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "Email Duplicated"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User Invalid Permission"),
    INVALID_OAUTH_PROVIDER(HttpStatus.UNAUTHORIZED, "Invalid Oauth2 Provider"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs");
    private final HttpStatus httpStatus;
    private final String message;
}
