package coms309.MeetMe.Users;

//import coms309.MeetMe.Users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User {

    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String email;
    private Date joiningDate;
    private Date lastSeen;
    private String availability; // TODO: Create Availability/Schedule object to pass in

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User), the cascade option tells springboot
     * to create the child entity if not present already (in this case it is laptop)
     * @JoinColumn specifies the ownership of the key i.e. The User table will contain a foreign key from the laptop table and the column name will be laptop_id
     */
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "laptop_id")
//    private Laptop laptop;

     /*
     * @OneToMany tells springboot that one instance of User can map to multiple instances of Phone OR one user row can map to multiple rows of the phone table 
     */
//    @OneToMany
//    private List<Phone> phones;

     // =============================== Constructors ================================== //


    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
    }

    public User() {
        this.name = "anonymous";
        this.password = "password";
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
    }


    
    // =============================== Getters and Setters for each field ================================== //


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getJoiningDate(){
        return joiningDate;
    }

    public String getAvailability() { return availability; }

    public void setAvailability(String availability) { this.availability = availability; }
    
}
