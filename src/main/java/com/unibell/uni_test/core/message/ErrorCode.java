package com.unibell.uni_test.core.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    CLIENT_NOT_FOUND(404, "Client not found", HttpStatus.NOT_FOUND),
    WRONG_CLIENT_NAME_NOT_FOUND(404, "Client not found try another name", HttpStatus.NOT_FOUND),
    WRONG_TYPE(400, "This type not exist - only 'phone' or 'email'", HttpStatus.BAD_REQUEST),
    DATA_NOT_FOUND(404, "NOT FOUND", HttpStatus.NOT_FOUND),
    VALIDATION_FAILED(400, "Validation Failed", HttpStatus.BAD_REQUEST);

    private final int value;

    private final String error;

    private final HttpStatus status;
}
