package coms309.MeetMe.PushNotifications.service;

import coms309.MeetMe.PushNotifications.firebase.FCMService;
import coms309.MeetMe.PushNotifications.model.PushNotificationRequest;
import coms309.MeetMe.PushNotifications.model.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    @Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;


    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public void sendPushNotification(String title, String message, Topic topic) {
        try {
            fcmService.sendMessageWithoutData(new PushNotificationRequest(title, message, topic));
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationToToken(String title, String message, Topic topic, String token) { 
        try {
            fcmService.sendMessageToToken(new PushNotificationRequest(title, message, topic, token));
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


    // public void sendPushNotification(PushNotificationRequest request) {
    //     try {
    //         fcmService.sendMessage(getSamplePayloadData(), request);
    //     } catch (InterruptedException | ExecutionException e) {
    //         logger.error(e.getMessage());
    //     }
    // }


    // public void sendPushNotificationWithoutData(PushNotificationRequest request) {
    //     try {
    //         fcmService.sendMessageWithoutData(request);
    //     } catch (InterruptedException | ExecutionException e) {
    //         logger.error(e.getMessage());
    //     }
    // }


    // public void sendPushNotificationToToken(PushNotificationRequest request) {
    //     try {
    //         fcmService.sendMessageToToken(request);
    //     } catch (InterruptedException | ExecutionException e) {
    //         logger.error(e.getMessage());
    //     }
    // }


    // private Map<String, String> getSamplePayloadData() {
    //     Map<String, String> pushData = new HashMap<>();
    //     pushData.put("messageId", defaults.get("payloadMessageId"));
    //     pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
    //     return pushData;
    // }
}