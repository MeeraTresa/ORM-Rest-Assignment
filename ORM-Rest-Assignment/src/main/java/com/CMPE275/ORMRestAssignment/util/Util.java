package com.CMPE275.ORMRestAssignment.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Util {
    public static HttpHeaders setContentTypeAndReturnHeaders(String format) {
        HttpHeaders responseHeadersObj = new HttpHeaders();
        if (format != null && !format.isEmpty() && "xml".equalsIgnoreCase(format)) {
            responseHeadersObj.setContentType(MediaType.APPLICATION_XML);
        } else {
            responseHeadersObj.setContentType(MediaType.APPLICATION_JSON);
        }
        return responseHeadersObj;
    }
}
