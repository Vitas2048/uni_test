package com.unibell.uni_test.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
public class ErrorResponse {
    private ErrorCode code;
    private String message;
}
