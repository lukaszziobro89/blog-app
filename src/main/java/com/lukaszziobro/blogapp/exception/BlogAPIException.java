package com.lukaszziobro.blogapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BlogAPIException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

}
