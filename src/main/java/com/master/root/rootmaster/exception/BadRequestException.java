package com.master.root.rootmaster.exception;

import javax.validation.ValidationException;

public class BadRequestException extends ValidationException {

    public BadRequestException(String message) {
        super(message);
    }
}
