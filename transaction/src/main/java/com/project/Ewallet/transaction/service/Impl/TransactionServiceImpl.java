package com.project.Ewallet.transaction.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Ewallet.transaction.service.TransactionService;
import com.project.Ewallet.transaction.service.resources.NotificationRequest;
import com.project.Ewallet.transaction.service.resources.TransactionRequest;
import com.project.Ewallet.transaction.service.resources.WalletTransactionRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    ObjectMapper mapper = new ObjectMapper();


    @Override
    public boolean performTransaction(Long userId, TransactionRequest transactionRequest) {
        try{
        WalletTransactionRequest walletTransactionRequest = WalletTransactionRequest.builder()
                .senderId(userId)
                .receiverId(transactionRequest.getReceiverId())
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .transactionType(transactionRequest.getTransactionType())
                .build();
        ResponseEntity<Boolean> response = restTemplate.postForEntity("http://localhost:8082/wallet/transaction", walletTransactionRequest, Boolean.class);

        String content = Strings.EMPTY;

        if (response.getStatusCode().is2xxSuccessful()) {
            NotificationRequest senderNotificationRequest = NotificationRequest.builder().userId(userId)
                    .transactionStatus("SUCCESS").userType("SENDER").amount(transactionRequest.getAmount()).build();
            content = mapper.writeValueAsString(senderNotificationRequest);
            kafkaTemplate.send("notification-topic", content);

            NotificationRequest receiverNotificationRequest = NotificationRequest.builder().userId(transactionRequest.getReceiverId())
                    .transactionStatus("SUCCESS").userType("RECEIVER").amount(transactionRequest.getAmount()).build();
            content = mapper.writeValueAsString(receiverNotificationRequest);
            kafkaTemplate.send("notification-topic", content);

        } else {
            NotificationRequest senderNotificationRequest = NotificationRequest.builder().userId(userId)
                    .transactionStatus("FAILURE").userType("SENDER").amount(transactionRequest.getAmount()).build();
            content = mapper.writeValueAsString(senderNotificationRequest);
            kafkaTemplate.send("notification-topic", content);
        }
        return response.getStatusCode().is2xxSuccessful();
    } catch(JsonProcessingException ex){
            return false;
        }
    }
}
