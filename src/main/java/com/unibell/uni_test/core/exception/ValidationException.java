package com.unibell.uni_test.core.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ValidationException extends RuntimeException {

    private final BindingResult errors;

    public List<String> getMessages() {
        return getValidationMessage(this.errors);
    }

    @Override
    public String getMessage() {
        return this.getMessages().toString();
    }

    private static List<String> getValidationMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ValidationException::getValidationMessage)
                .collect(Collectors.toList());
    }

    private static String getValidationMessage(ObjectError error) {
        if (error instanceof FieldError) {
            var fieldError = (FieldError) error;
            var className = fieldError.getObjectName();
            var property = fieldError.getField();
            var invalidValue = fieldError.getRejectedValue();
            var message = fieldError.getDefaultMessage();
            return String.format("%s.%s %s, but it was %s", className, property, message, invalidValue);
        }
        return String.format("%s: %s", error.getObjectName(), error.getDefaultMessage());
    }
}
