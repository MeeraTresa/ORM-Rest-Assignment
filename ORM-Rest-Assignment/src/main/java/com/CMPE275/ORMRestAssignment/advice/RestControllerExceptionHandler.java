package com.CMPE275.ORMRestAssignment.advice;



import com.CMPE275.ORMRestAssignment.exception.CustomException;
import com.CMPE275.ORMRestAssignment.util.Util;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(
            CustomException ex) {
        return new ResponseEntity<>(ex.getMessage(),
                Util.setContentTypeAndReturnHeaders(ex.getFormat()),
                ex.getStatus());
    }


}
