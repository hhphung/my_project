package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Meeting.MeetingRepository;
import coms309.MeetMe.MeetingInvite.MeetingInvite;
import coms309.MeetMe.User.*;
import coms309.MeetMe.chat.Message;
import coms309.MeetMe.chat.MessageRepository;
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

    @Mock
    MessageRepository messageRepository;


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

    @Test
    public void message(){
        User one = new User("hoiproa0", "hoiproa0");
        Meeting meeting  = new Meeting();
        Message message = new Message(one,meeting, "jajaja");
        User one1 = new User("hoiproa0", "hoiproa0");
        Meeting meeting1  = new Meeting();
        Message message1 = new Message(one,meeting, "jajaja");
        List<Message> list = new ArrayList<Message>();
        list.add(message);
        list.add(message1);
        when(messageRepository.findAll()).thenReturn(list);
        List<Message> acctList = messageRepository.findAll();
        assertEquals(2, acctList.size());
        verify(messageRepository, times(1)).findAll();
    }




/*


    @Test
    public void acceptrFriendRequest(){

        UserNamePair us = new UserNamePair("Fred", "Max");

        when(userController.acceptFriendRequestNames(us)).thenReturn("hehehe");

        String h = userController.acceptFriendRequestNames( us);
        assertEquals("hoiproa2", h);
    }
/*

    @Test
    public void getUserFriendRequest(){

        List<UserShadow> list = new ArrayList<UserShadow>();
        User one = new User("hoiproa0", "hoiproa0");
        User two = new User("hoiprao1", "hoiprao1");
        User three = new User("hoiproa2", "hoiprao2");

        UserShadow acctOne = new UserShadow(one);
        UserShadow acctTwo = new UserShadow(two);
        UserShadow acctThree =new UserShadow(three);
        list.add(acctOne);
        list.add(acctTwo);
        list.add(acctThree);

        when(userController.getFriendRequestsReceived("hoiproa3")).thenReturn(list);

        assertEquals(3, list.size());
        verify(repo, times(1)).findAll();
    }
*/
}

