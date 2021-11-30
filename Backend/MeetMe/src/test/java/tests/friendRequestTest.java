package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserController;
import coms309.MeetMe.User.UserRepository;
import coms309.MeetMe.FriendRequest.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.PostMapping;

public class friendRequestTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository userRepository;

    @Mock
    FriendRequestRepository friendRequestRepository;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    // @Test
    // FriendRequest findByUsers() {

    //     when(friendRequestRepository.findByUsers(0, 1)).thenReturn();

    //     assertEquals(3, acctList.size());
    //     verify(repo, times(1)).findAll();
    // }

    // @Test
    // FriendRequest findById() {
    //     findById(int id)
    // }

    // @Test
    // void deleteFriendRequest() {
    //     deleteFriendRequest(int id)
    // }

    // @Test
    // List<FriendRequest> findFriendRequestsSent() {
    //     findFriendRequestsSent(int id)
    // }

    // @Test
    // List<FriendRequest> findFriendRequestsReceived() {
    //     findFriendRequestsReceived(int id)
    // }
}

