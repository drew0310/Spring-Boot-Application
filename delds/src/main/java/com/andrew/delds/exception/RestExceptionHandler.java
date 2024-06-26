package com.andrew.delds.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public Map<String, String> handleObjectNotFoundException(ObjectNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("statusCode", String.valueOf(HttpStatus.NOT_FOUND.value()));
        errorMap.put("errorMessage", ex.getMessage());
        log.error("Status code "+HttpStatus.NOT_FOUND.value()+" - "+ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("statusCode", String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()));
        errorMap.put("errorMessage", ex.getMessage()+" for this URL endpoint");
        log.error("Status code "+HttpStatus.METHOD_NOT_ALLOWED.value()+" - "+ex.getMessage()+" for the given URL endpoint");
        return errorMap;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public Map<String, String> handleNullPointerException(NullPointerException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("statusCode", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        errorMap.put("errorMessage", "Null object (or) dependency not injected properly: "+ex.getMessage());
        log.error("Status code "+HttpStatus.INTERNAL_SERVER_ERROR.value()+" - Null object (or) dependency not injected properly: "+ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Map<String, String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("statusCode", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorMap.put("errorMessage", "Invalid URL: no handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL());
        log.error("Status code "+HttpStatus.BAD_REQUEST.value()+" - Invalid URL: no handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL());
        return errorMap;
    }



}
