package com.project.Ewallet.User.service.resource;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

     private Long receiverId;
     private Double amount;
     private String description;
     private String transactionType;
}
