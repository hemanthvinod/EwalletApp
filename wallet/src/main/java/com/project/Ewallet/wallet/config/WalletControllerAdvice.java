package com.project.Ewallet.wallet.config;

import com.project.Ewallet.wallet.exception.WalletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class WalletControllerAdvice {

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<?> handleUserException(WalletException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("type", exception.getType());
        errorMap.put("message", exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
