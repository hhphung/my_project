package coms309.MeetMe.PushNotifications.model;

public class PushNotificationRequest {

    private String title;
    private String message;
    private String topic;

    public PushNotificationRequest() {
    }

    public PushNotificationRequest(String title, String messageBody, String topic) {
        this.title = title;
        this.message = messageBody;
        this.topic = topic;
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
}
