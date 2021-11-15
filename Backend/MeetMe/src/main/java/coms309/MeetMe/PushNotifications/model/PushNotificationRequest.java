package coms309.MeetMe.PushNotifications.model;

public class PushNotificationRequest {

    private String title;
    private String message;
    private String topic;
    private String token;

    public PushNotificationRequest() {
    }

    public PushNotificationRequest(String title, String messageBody, Topic topic) {
        this.title = title;
        this.message = messageBody;
        this.topic = topic.name().toLowerCase();
    }

    public PushNotificationRequest(String title, String messageBody, Topic topic, String token) {
        this.title = title;
        this.message = messageBody;
        this.topic = topic.name().toLowerCase();
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
