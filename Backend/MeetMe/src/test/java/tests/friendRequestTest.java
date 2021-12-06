package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import coms309.MeetMe.User.Role;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserController;
import coms309.MeetMe.User.UserNamePair;
import coms309.MeetMe.User.UserRepository;
import coms309.MeetMe.User.UserShadow;
import coms309.MeetMe.FriendRequest.*;
import coms309.MeetMe.Stringy.Stringy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class friendRequestTest {

    @Mock
    UserController userController;

    @Mock
    UserRepository userRepository;

    @Mock
    FriendRequestRepository friendRequestRepository;

    UserShadow Alex, Max, Bob, Joel, Rick;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        userRepository.deleteAll();
        friendRequestRepository.deleteAll();
        
        userController.createUser(new User("Alex", "arst", Role.VIEWER));
        userController.createUser(new User("Max", "arst", Role.VIEWER));
        userController.createUser(new User("Bob", "arst", Role.VIEWER));
        userController.createUser(new User("Joel", "arst", Role.VIEWER));
        userController.createUser(new User("Rick", "arst", Role.ADMIN));

        Alex = userController.getUserByName("Alex");
        Max = userController.getUserByName("Max");
        Bob = userController.getUserByName("Bob");
        Joel = userController.getUserByName("Joel");
        Rick = userController.getUserByName("Rick");
    }


    @Test
    public void testGetFriendsWithoutFriends() {

        // Build expected friends
        List<UserShadow> expectedFriends = new ArrayList<UserShadow>();
        
        // Call getFriends
        when(userController.getFriends("Alex")).thenReturn(expectedFriends);

        // Expect List<UserShadow>
        expectedFriends = userController.getFriends("Alex");
        assertEquals(0, expectedFriends.size());
        verify(userController, times(1)).getFriends("Alex");
    }


    @Test
    public void testGetFriendRequestsSent() {

        // Build User with friends
        userController.sendFriendRequest(new UserNamePair("Alex", "Max"));
        userController.sendFriendRequest(new UserNamePair("Bob", "Alex"));
        userController.sendFriendRequest(new UserNamePair("Alex", "Joel"));

        userController.acceptFriendRequestNames(new UserNamePair("Alex", "Max"));

        // Build expected friends
        List<UserShadow> expectedFriends = new ArrayList<UserShadow>();
        
        expectedFriends.add(Max);
        expectedFriends.add(Joel);

        // Call getFriends
        when(userController.getFriendRequestsSent("Alex")).thenReturn(expectedFriends);

        // Expect List<UserShadow>
        expectedFriends = userController.getFriendRequestsSent("Alex");
        assertEquals(2, expectedFriends.size());
        verify(userController, times(1)).getFriendRequestsSent("Alex");
    }

    @Test
    public void testGetFriendRequestsReceived() {

        // Build User with friends
        userController.sendFriendRequest(new UserNamePair("Alex", "Max"));
        userController.sendFriendRequest(new UserNamePair("Bob", "Alex"));
        userController.sendFriendRequest(new UserNamePair("Alex", "Joel"));
        userController.sendFriendRequest(new UserNamePair("Max", "Joel"));

        userController.acceptFriendRequestNames(new UserNamePair("Alex", "Max"));
        userController.acceptFriendRequestNames(new UserNamePair("Max", "Joel"));

        // -- USING Alex --

        // Build expected friends
        List<UserShadow> expectedFriends = new ArrayList<UserShadow>();
        
        expectedFriends.add(Bob);

        // Call getFriends
        when(userController.getFriendRequestsReceived("Alex")).thenReturn(expectedFriends);

        // Expect List<UserShadow>
        expectedFriends = userController.getFriendRequestsReceived("Alex");
        assertEquals(1, expectedFriends.size());
        verify(userController, times(1)).getFriendRequestsReceived("Alex");

        // -- USING Joel --
        
        // Build expected friends
        expectedFriends = new ArrayList<UserShadow>();
        
        expectedFriends.add(Max);
        expectedFriends.add(Alex);

        // Call getFriends
        when(userController.getFriendRequestsReceived("Joel")).thenReturn(expectedFriends);

        // Expect List<UserShadow>
        expectedFriends = userController.getFriendRequestsReceived("Joel");
        assertEquals(2, expectedFriends.size());
        verify(userController, times(1)).getFriendRequestsReceived("Joel");
    }

    @Test
    public void testGetFriendsWithFriends() {
        // Build User with friends
        userController.sendFriendRequest(new UserNamePair("Alex", "Max"));
        userController.sendFriendRequest(new UserNamePair("Bob", "Alex"));
        userController.sendFriendRequest(new UserNamePair("Alex", "Joel"));

        userController.acceptFriendRequestNames(new UserNamePair("Alex", "Max"));
        userController.acceptFriendRequestNames(new UserNamePair("Bob", "Alex"));
        userController.acceptFriendRequestNames(new UserNamePair("Alex", "Joel"));


        // Build expected friends
        List<UserShadow> expectedFriends = new ArrayList<UserShadow>();
        
        expectedFriends.add(Max);
        expectedFriends.add(Bob);
        expectedFriends.add(Joel);

        // Call getFriends
        when(userController.getFriends("Alex")).thenReturn(expectedFriends);


        // Expect List<UserShadow>
        expectedFriends = userController.getFriends("Alex");
        assertEquals(3, expectedFriends.size());
        verify(userController, times(1)).getFriends("Alex");
    }
}

