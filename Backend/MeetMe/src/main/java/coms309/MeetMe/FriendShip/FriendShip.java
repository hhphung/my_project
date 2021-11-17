package coms309.MeetMe.FriendShip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.MeetMe.User.User;

import javax.persistence.*;

@Entity
public class FriendShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;




    @Column(nullable = false, name = "senderId")
    private Integer senderId;

    @Column(nullable = false, name = "receiverId")
    private Integer receiverId;

    public FriendShip(Integer senderId, Integer receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }





}
