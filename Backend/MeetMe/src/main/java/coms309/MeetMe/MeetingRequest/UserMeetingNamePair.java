package coms309.MeetMe.MeetingRequest;

public class UserMeetingNamePair {
  private String userName, meetingName;

  UserMeetingNamePair(String userName, String meetingName) {
      this.userName = userName;
      this.meetingName = meetingName;
  }

  public String getUserName() { return userName; }

  public String getMeetingName() { return meetingName; }

  public void setUserName(String userName) { this.userName = userName; }

  public void setMeetingName(String meetingName) { this.meetingName = meetingName; }

  public boolean isInvalid() { return (userName == null || meetingName == null); }
}
