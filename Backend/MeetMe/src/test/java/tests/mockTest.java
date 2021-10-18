package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import coms309.MeetMe.Location.Location;
import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Meeting.MeetingRepository;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserController;
import coms309.MeetMe.User.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.PostMapping;

public class mockTest {

    @InjectMocks
    UserController userController;


    @Mock
    UserRepository repo;

    @Mock
    MeetingRepository meet;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getAlUserTest() {
        List<User> list = new ArrayList<User>();
        User acctOne = new User("hoiproa0", "hoiproa0");
        User acctTwo = new User("hoiprao1", "hoiprao1");
        User acctThree = new User("hoiproa2", "hoiprao2");

        list.add(acctOne);
        list.add(acctTwo);
        list.add(acctThree);

        when(repo.findAll()).thenReturn(list);

        List<User> acctList = repo.findAll();

        assertEquals(3, acctList.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void getUserbyName(){
        when(repo.findByName("hoiproa2")).thenReturn(new User ("hoiproa2", "hoiproa2"));

        User acct = repo.findByName("hoiproa2");
        assertEquals("hoiproa2", acct.getPassword());
    }

    @Test
    @PostMapping
    public void createUserTest(){
        User  hoi = new User ("hoi", "phung");
        User test = new User ("test", "test");


        User acct = repo.findByName("hoiproa2");
        assertEquals("hoiproa2", acct.getPassword());
    }



    @Test
    public void getAllMeetingTest() {
        List<Meeting> list = new ArrayList<Meeting>();
        User acctOne = new User("hoiproa0", "hoiproa0");
        User acctTwo = new User("hoiprao1", "hoiprao1");
        User acctThree = new User("hoiproa2", "hoiprao2");

        Location loc  = new Location("street", "city", "state", 52641, "country");

        Meeting one = new Meeting(acctOne, "first", "ahahaha", "0000", loc);
        Meeting two = new Meeting(acctTwo, "second", "ahahaha", "0000", loc);
        Meeting three = new Meeting(acctThree, "last", "ahahaha", "0000", loc);

        list.add(one);
        list.add(two);
        list.add(three);
        when(meet.findAll()).thenReturn(list);
        List<Meeting> acctList = meet.findAll();
        assertEquals(3, acctList.size());
        verify(meet, times(1)).findAll();
    }

    @Test
    public void getMeetingbyName(){
        User acctOne = new User("hoiproa0", "hoiproa0");
        Location loc  = new Location("street", "city", "state", 52641, "country");
        when(meet.findByName("first")).thenReturn(new Meeting(acctOne, "first", "ahahaha", "0000", loc));

        Meeting meeting = meet.findByName("first");
        User user = meeting.getAdmin();
        assertEquals("hoiproa0", user.getName());
    }
}

