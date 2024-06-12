package com.project.Ewallet.User.config;

import com.project.Ewallet.User.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserControllerAdvice {


    @ExceptionHandler(UserException.class)
    public String handleUserException(UserException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("type", exception.getType());
        errorMap.put("message", exception.getMessage());
        return errorMap.toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error-> errorMap.put(error.getField(),error.getDefaultMessage()));
        return errorMap.toString();
    }

}
