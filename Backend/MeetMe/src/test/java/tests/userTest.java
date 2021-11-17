package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

public class userTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository repo;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getAlUsers() {
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
}

