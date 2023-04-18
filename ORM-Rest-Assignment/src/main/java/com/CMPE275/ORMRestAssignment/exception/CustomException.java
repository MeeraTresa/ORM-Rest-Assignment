package com.CMPE275.ORMRestAssignment.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {
    HttpStatus status;
    String format;

    public CustomException() {
        super();
    }


    public CustomException(String message, HttpStatus status, String format) {
        super(message);
        this.status = status;
        this.format = format;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
