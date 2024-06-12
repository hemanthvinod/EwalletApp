package com.project.Ewallet.wallet.consumer;

import com.project.Ewallet.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserActionConsumer {

    @Autowired
    WalletService walletService;

    @Value("${kafka.topic.user-created}")
    private String USER_CREATED_TOPIC;

    @Value("${kafka.topic.user-deleted}")
    private String USER_DELETED_TOPIC;

    Logger logger = LoggerFactory.getLogger(UserActionConsumer.class);

    @KafkaListener(topics="${kafka.topic.user-created}", groupId = "walletGroup")
    public void consumerUserCreated(String message){
        logger.info(String.format("Message received -> %s", message));
        walletService.createWallet(Long.valueOf(message));
    }
    @KafkaListener(topics="${kafka.topic.user-deleted}", groupId = "walletGroup")
    public void consumerUserDeleted(String message){
        logger.info(String.format("Message received -> %s", message));
        walletService.deleteWallet(Long.valueOf(message));
    }


}
