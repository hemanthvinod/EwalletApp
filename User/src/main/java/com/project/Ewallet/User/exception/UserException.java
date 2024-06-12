package com.project.Ewallet.User.exception;

public class UserException extends RuntimeException{

    private String type;
    private String message;

    public UserException(String type, String message){
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

}
