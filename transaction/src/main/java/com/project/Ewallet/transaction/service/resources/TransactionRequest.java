package com.project.Ewallet.transaction.service.resources;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

    private Long receiverId;
    private Double amount;
    private String description;
    private String transactionType;

    public  String toString(){
        return receiverId+" "+amount+" "+description+" "+transactionType;
    }

}
