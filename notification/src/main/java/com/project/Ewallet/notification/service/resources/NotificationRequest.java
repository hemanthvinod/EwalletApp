package com.project.Ewallet.notification.service.resources;


import lombok.*;
import org.springframework.stereotype.Service;

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

    public String toString(){
        return userId+" "+amount+" "+userType+" "+transactionStatus;
    }

}
