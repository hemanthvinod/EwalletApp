package com.project.Ewallet.transaction.service.resources;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
     private Long userId;
     private Double amount;
     private String userType;
     private String transactionStatus;
}

