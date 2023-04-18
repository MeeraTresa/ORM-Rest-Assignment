package com.CMPE275.ORMRestAssignment.exception;

import org.springframework.http.HttpStatus;

public class RecordDoesNotExistException extends CustomException {
    public RecordDoesNotExistException(String msg, String format) {
        super(msg, HttpStatus.NOT_FOUND, format);
    }
}
