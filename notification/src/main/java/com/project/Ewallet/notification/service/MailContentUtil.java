package com.project.Ewallet.notification.service;

public class MailContentUtil {

    public static String senderSuccessMessage(String username, Double amount){
        StringBuilder sb = new StringBuilder();
        sb.append("Hi "+ username+"\n");
        sb.append("Your account is debited of "+amount+" successfully\n");
        return sb.toString();
    }
    public static String receiverSuccessMessage(String username, Double amount){
        StringBuilder sb = new StringBuilder();
        sb.append("Hi "+ username+"\n");
        sb.append("Your account is credited with "+amount+" successfully");
        return sb.toString();
    }
    public static String senderFailureMessage(String username, Double amount){
        StringBuilder sb = new StringBuilder();
        sb.append("Hi "+ username+"\n");
        sb.append("Your transaction of amount "+amount+" has failed\n");
        sb.append("Please retry after sometime\n");
        return sb.toString();
    }

    public static String  transactionSuccessfulSubject() {
        return "Transaction successful";
    }

    public static String transactionFailedSubject(){
        return "Transaction failed";
    }
}
