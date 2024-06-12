package com.project.Ewallet.notification.service.impl;

import com.project.Ewallet.notification.service.MailContentUtil;
import com.project.Ewallet.notification.service.NotificationService;
import com.project.Ewallet.notification.service.resources.NotificationRequest;
import com.project.Ewallet.notification.service.resources.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public void sendNotification(NotificationRequest notificationRequest) {
        // sender successful transaction message
        if(notificationRequest.getTransactionStatus().equalsIgnoreCase("SUCCESS")
        && notificationRequest.getUserType().equalsIgnoreCase("SENDER")){
            UserResponse response = restTemplate.getForEntity("http://localhost:8081/user/"+notificationRequest.getUserId(), UserResponse.class).getBody();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(MailContentUtil.transactionSuccessfulSubject());
            mailMessage.setText(MailContentUtil.senderSuccessMessage(response.getUsername(), notificationRequest.getAmount()));
            mailMessage.setTo(response.getEmail());
            mailSender.send(mailMessage);
        }

        // receiver successful transaction message
        if(notificationRequest.getTransactionStatus().equalsIgnoreCase("SUCCESS")
                && notificationRequest.getUserType().equalsIgnoreCase("RECEIVER")){
            UserResponse response = restTemplate.getForEntity("http://localhost:8081/user/"+notificationRequest.getUserId(), UserResponse.class).getBody();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(MailContentUtil.transactionSuccessfulSubject());
            mailMessage.setText(MailContentUtil.receiverSuccessMessage(response.getUsername(), notificationRequest.getAmount()));
            mailMessage.setTo(response.getEmail());
            mailSender.send(mailMessage);
        }

        //sender transaction failure message
        if(notificationRequest.getTransactionStatus().equalsIgnoreCase("FAILURE")
                && notificationRequest.getUserType().equalsIgnoreCase("SENDER")){
            UserResponse response = restTemplate.getForEntity("http://localhost:8081/user/"+notificationRequest.getUserId(), UserResponse.class).getBody();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(MailContentUtil.transactionFailedSubject());
            mailMessage.setText(MailContentUtil.senderFailureMessage(response.getUsername(), notificationRequest.getAmount()));
            mailMessage.setTo(response.getEmail());
            mailSender.send(mailMessage);
        }
    }
}
