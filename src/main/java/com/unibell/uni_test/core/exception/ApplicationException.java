package com.unibell.uni_test.core.exception;


import com.unibell.uni_test.core.message.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Getter@Setter
@AllArgsConstructor
public class ApplicationException extends Throwable implements Supplier<ApplicationException> {
    private ErrorCode code;

    @Override
    public ApplicationException get() {
        return new ApplicationException(code);
    }
}
