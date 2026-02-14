package com.example.ecommerce.ecommerce.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> notFoundHandler(UsernameNotFoundException ex)
    {
        return ResponseEntity.status(401).body("Username not found!! from handler");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> forbiddenUserHandler(AccessDeniedException ex)
    {
        return ResponseEntity.status(403).body("Access not found!! from handler");
    }
}
