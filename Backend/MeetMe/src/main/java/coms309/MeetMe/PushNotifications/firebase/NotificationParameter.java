package coms309.MeetMe.PushNotifications.firebase;


public enum NotificationParameter {
    SOUND("default"),
    COLOR("#0000FF");

    private String value;

    NotificationParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
