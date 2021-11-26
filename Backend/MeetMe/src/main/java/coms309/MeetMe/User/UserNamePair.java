package coms309.MeetMe.User;

public class UserNamePair {
  private String userNameA, userNameB;

  UserNamePair(String userNameA, String userNameB) {
      this.userNameA = userNameA;
      this.userNameB = userNameB;
  }

  public String getUserNameA() { return userNameA; }

  public String getUserNameB() { return userNameB; }

  public void setUserNameA(String userNameA) { this.userNameA = userNameA; }

  public void setUserNameB(String userNameB) { this.userNameB = userNameB; }

  public boolean isInvalid() { return (userNameA == null || userNameB == null || userNameA.equals(userNameB)); }
}
