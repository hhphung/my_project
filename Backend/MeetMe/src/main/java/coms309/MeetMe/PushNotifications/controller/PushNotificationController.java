/**
 * Use this as an example Controller model GET/POST requests with
 *
 * This whole PushNotifications package was used from:
 * https://github.com/firststepitsolution/spring-boot-push-notification-with-fcm
 */

package coms309.MeetMe.PushNotifications.controller;

import coms309.MeetMe.PushNotifications.model.PushNotificationRequest;
import coms309.MeetMe.PushNotifications.model.PushNotificationResponse;
import coms309.MeetMe.PushNotifications.model.Topic;
import coms309.MeetMe.PushNotifications.service.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PushNotificationController {

    private PushNotificationService pushNotificationService;

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    // @PostMapping("/addFriend")
    // public ResponseEntity sendNotification(@RequestBody String searchFriend) {
    //     // Lookup friend in database


    //     if (false) return new ResponseEntity<>("User was not found", HttpStatus.CONFLICT);

    //     // If friend found, send out notification to friend's token
    //     pushNotificationService.sendPushNotificationToToken("title", "message", Topic.COMMON, "putTokenHere");

    //     return new ResponseEntity<>("User has been notified", HttpStatus.OK);
    // }
    
    // Examples

    // @PostMapping("/notification/topic")
    // public ResponseEntity sendNotification(@RequestBody PushNotificationRequest request) {
    //     pushNotificationService.sendPushNotificationWithoutData(request);
    //     return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    // }

    // @PostMapping("/notification/token")
    // public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
    //     pushNotificationService.sendPushNotificationToToken(request);
    //     return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    // }

    // @PostMapping("/notification/data")
    // public ResponseEntity sendDataNotification(@RequestBody PushNotificationRequest request) {
    //     pushNotificationService.sendPushNotification(request);
    //     return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    // }
}
