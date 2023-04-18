package com.CMPE275.ORMRestAssignment.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {
    public BadRequestException(String msg, String format) {
        super(msg, HttpStatus.BAD_REQUEST, format);
    }
}
