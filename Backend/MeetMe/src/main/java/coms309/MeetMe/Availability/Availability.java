package coms309.MeetMe.Availability;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms309.MeetMe.User.User;


@Entity(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false, targetEntity =  User.class)
    @JoinColumn( unique = true, nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private boolean [] availability = new boolean[168];


    // =============================== Constructors ================================== //


    public Availability() {
    }

    public Availability(boolean[] availability, User user) {
        this.availability = availability;
        this.user = user;
    }


    // ============================= Getters/Setters ================================ //

    
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
