package com.unibell.uni_test.config.spring;

import com.unibell.uni_test.core.exception.ApplicationException;
import com.unibell.uni_test.core.exception.ValidationException;
import com.unibell.uni_test.core.message.ErrorCode;
import com.unibell.uni_test.core.message.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    private ResponseEntity<ErrorResponse> handle(ApplicationException e) {
        log.debug("Handle error: {}", e.getCode().getValue());
        return ResponseEntity.status(e.getCode().getStatus())
                .body(new ErrorResponse(e.getCode(), e.getCode().getError()));
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<ErrorResponse> handle(ValidationException e) {
        log.debug("Handle error: {}", e.getMessage());
        return ResponseEntity.status(ErrorCode.VALIDATION_FAILED.getStatus())
                .body(new ErrorResponse(ErrorCode.VALIDATION_FAILED, e.getMessage()));
    }
}
