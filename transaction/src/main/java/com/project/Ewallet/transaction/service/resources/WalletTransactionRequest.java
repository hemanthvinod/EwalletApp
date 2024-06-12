package com.project.Ewallet.transaction.service.resources;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletTransactionRequest {

        private Long senderId;
        private Long receiverId;
        private Double amount;
        private String description;
        private String transactionType;

}


