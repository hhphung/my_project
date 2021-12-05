package coms309.MeetMe.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class UserShadow {

    private Integer id;

    private String name;
    private String email;
    private Date joiningDate;
    private Date lastSeen;
    private Role role;

    private List<Integer> meetingParticipation;
    private List<String> friends;

    public UserShadow() {

    }

    public UserShadow(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.joiningDate = user.getJoiningDate();
        this.lastSeen = user.getLastSeen();
        this.role = user.getRole();
        this.meetingParticipation = user.getMeetingParticipation();
        this.friends = new ArrayList<String>();
        Set<User> userFriends = user.getFriends();
        userFriends.forEach(friend -> {
            friends.add(friend.getName());
        });
    }

    public static List<UserShadow> build(List<User> users) {
        List<UserShadow> rebuild = new ArrayList<UserShadow>();
        users.forEach(user -> {
            rebuild.add(new UserShadow(user));
        });
        return rebuild;
    }

    public static List<UserShadow> build(Set<User> users) {
        List<UserShadow> rebuild = new ArrayList<UserShadow>();
        users.forEach(user -> {
            rebuild.add(new UserShadow(user));
        });
        return rebuild;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public String getEmail() {
        return email;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public Role getRole() {
        return role;
    }

    public List<Integer> getMeetingParticipation() {
        return meetingParticipation;
    }

    public List<String> getFriends() {
        return friends;
    }
}
