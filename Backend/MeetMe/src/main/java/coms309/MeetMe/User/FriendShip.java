package coms309.MeetMe.User;

public class FriendShip {
  private int senderId;

      private int recieverId;


    public FriendShip(int senderId, int recieverId) {
        this.senderId = senderId;
        this.recieverId = recieverId;
    }


    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(int recieverId) {
        this.recieverId = recieverId;
    }
}
