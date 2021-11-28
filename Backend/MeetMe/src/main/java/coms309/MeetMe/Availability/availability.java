package coms309.MeetMe.Availability;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.firebase.database.annotations.NotNull;
import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Stringy.Stringy;
import coms309.MeetMe.User.User;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false, targetEntity =  User.class)
    @JoinColumn( unique = true, nullable = false)
    @JsonIgnore
    private User user;




    @Column(nullable = false)
    private boolean [] availability = new boolean[168]; // TODO: Create Availability/Schedule object to pass in

     // =============================== Constructors ================================== //


    public availability() {
    }

    public availability(boolean[] availability, User user) {
        this.availability = availability;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean[] getAvailability() {
        return availability;
    }

    public void setAvailability(boolean[] availability) {
        this.availability = availability;
    }
}
