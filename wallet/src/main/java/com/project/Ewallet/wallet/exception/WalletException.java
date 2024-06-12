package com.project.Ewallet.wallet.exception;

public class WalletException extends RuntimeException{

    private String type;
    private String message;

    public WalletException(String type, String message){
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
