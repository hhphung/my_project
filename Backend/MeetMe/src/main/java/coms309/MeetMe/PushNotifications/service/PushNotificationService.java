package coms309.MeetMe.PushNotifications.service;

import coms309.MeetMe.PushNotifications.firebase.FCMService;
import coms309.MeetMe.PushNotifications.model.PushNotificationRequest;

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

    public void sendPushNotification(String title, String message, String topic) {
        try {
            fcmService.sendMessageWithoutData(new PushNotificationRequest(title, message, topic));
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }
}
