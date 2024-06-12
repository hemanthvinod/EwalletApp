package com.project.Ewallet.notification.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.Ewallet.notification.service.NotificationService;
import com.project.Ewallet.notification.service.resources.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    NotificationService notificationService;

    Logger logger =  LoggerFactory.getLogger(NotificationConsumer.class);

    ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "notification-topic", groupId = "NotificationGroup")
    public void consume(String message){
        logger.info("Consumed message :" + message);
        try{
            NotificationRequest notificationRequest = mapper.readValue(message,NotificationRequest.class);
            notificationService.sendNotification(notificationRequest);
        }catch(Exception ex){
            logger.error("Exception while reading the notification content");
        }
    }

}
