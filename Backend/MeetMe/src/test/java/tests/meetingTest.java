package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import coms309.MeetMe.Stringy.Stringy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coms309.MeetMe.Location.Location;
import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Meeting.MeetingRepository;
import coms309.MeetMe.MeetingInvite.MeetingInvite;
import coms309.MeetMe.MeetingInvite.MeetingInviteController;
import coms309.MeetMe.MeetingInvite.MeetingInviteRepository;
import coms309.MeetMe.MeetingInvite.MeetingInviteState;
import coms309.MeetMe.MeetingRequest.UserMeetingNamePair;
import coms309.MeetMe.User.Role;
import coms309.MeetMe.User.User;
import coms309.MeetMe.chat.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class meetingTest {

    @Mock
    MeetingRepository meet;
    @Mock
    MeetingInviteRepository invite;

    @Mock
    MeetingInviteController inviteController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllMeetings() {
        List<Meeting> list = new ArrayList<Meeting>();
        User acctOne = new User("hoiproa0", "hoiproa0", Role.ADMIN);
        User acctTwo = new User("hoiprao1", "hoiprao1", Role.ADMIN);
        User acctThree = new User("hoiproa2", "hoiprao2", Role.ADMIN);

        Location loc = new Location("street", "city", "state", 52641, "country");

        Meeting one = new Meeting(acctOne, "first", "ahahaha", "0000", 1, loc);
        Meeting two = new Meeting(acctTwo, "second", "ahahaha", "0000", 1, loc);
        Meeting three = new Meeting(acctThree, "last", "ahahaha", "0000", 1, loc);

        list.add(one);
        list.add(two);
        list.add(three);
        when(meet.findAll()).thenReturn(list);
        List<Meeting> acctList = meet.findAll();
        assertEquals(3, acctList.size());
        verify(meet, times(1)).findAll();
    }

    @Test
    public void getMeetingbyName() {
        User acctOne = new User("hoiproa0", "hoiproa0", Role.ADMIN);
        Location loc = new Location("street", "city", "state", 52641, "country");
        when(meet.findByName("first")).thenReturn(new Meeting(acctOne, "first", "ahahaha", "0000", 1, loc));

        Meeting meeting = meet.findByName("first");
        User user = meeting.getAdmin();
        assertEquals("hoiproa0", user.getName());
    }

    @Test
    public void getMeetingBySearch() {

        User dummyAdmin = new User("dummyUser", "dummyPassword", Role.ADMIN);
        Location loc = new Location("street", "city", "state", 12345, "country");

        Meeting one = new Meeting(dummyAdmin, "first1", "ahahaha", "0000", 1, loc);
        Meeting two = new Meeting(dummyAdmin, "second1", "ahahaha", "0000", 1, loc);
        Meeting three = new Meeting(dummyAdmin, "third1", "ahahaha", "0000", 1, loc);

        List<Meeting> search1 = new ArrayList<Meeting>(Arrays.asList(one));
        List<Meeting> search2 = new ArrayList<Meeting>(Arrays.asList(two, three));
        List<Meeting> search3 = new ArrayList<Meeting>(Arrays.asList(one, two, three));

        when(meet.findBySearch("first")).thenReturn(search1);
        when(meet.findBySearch("d")).thenReturn(search2);
        when(meet.findBySearch("1")).thenReturn(search3);

        List<Meeting> result = meet.findBySearch("first");
        assertEquals(1, result.size());
        verify(meet, times(1)).findBySearch("first");

        result = meet.findBySearch("d");
        assertEquals(2, result.size());
        verify(meet, times(1)).findBySearch("d");

        result = meet.findBySearch("1");
        assertEquals(3, result.size());
        verify(meet, times(1)).findBySearch("1");
    }

    @Test
    public void Invites(){
        User one = new User("hoiproa0", "hoiproa0");
        Meeting meeting  = new Meeting();
        MeetingInvite meetingInvite = new MeetingInvite(one, meeting);
        User one1 = new User("hoiproa0", "hoiproa0");
        Meeting meeting1  = new Meeting();
        MeetingInvite meetingInvite2 = new MeetingInvite(one1, meeting1);
        List<MeetingInvite> list = new ArrayList<MeetingInvite>();
        list.add(meetingInvite);
        list.add(meetingInvite2);
        when(invite.findAll()).thenReturn(list);
        List<MeetingInvite> acctList = invite.findAll();
        assertEquals(2, acctList.size());
        verify(invite, times(1)).findAll();
    }

    @Test
    public void getMeetInvitesByUserName(){
        Location loc = new Location("street", "city", "state", 12345, "country");
        User acctOne = new User("hoiproa0", "hoiproa0", Role.ADMIN);
        Meeting one = new Meeting(acctOne, "first1", "ahahaha", "0000", 1, loc);
        User acctTwo = new User("hoiprao1", "hoiprao1", Role.ADMIN);
        MeetingInvite temp = new MeetingInvite(acctTwo, one, MeetingInviteState.PENDING);
        List<MeetingInvite> list = new ArrayList<>();
        list.add(temp);
        UserMeetingNamePair x = new  UserMeetingNamePair(acctTwo.getName(), one.getName());
        when(inviteController.getByUsername(acctTwo.getName())).thenReturn(list);
        List<MeetingInvite> acctList = inviteController.getByUsername(acctTwo.getName());
        assertEquals(1, acctList.size());
        verify(inviteController, times(1)).getByUsername(acctTwo.getName());

    }


    @Test
    public void postMeetInvites(){
        Location loc = new Location("street", "city", "state", 12345, "country");
        User acctOne = new User("hoiproa0", "hoiproa0", Role.ADMIN);
        Meeting one = new Meeting(acctOne, "first1", "ahahaha", "0000", 1, loc);
        User acctTwo = new User("hoiprao1", "hoiprao1", Role.ADMIN);
        MeetingInvite temp = new MeetingInvite(acctTwo, one, MeetingInviteState.PENDING);
        List<MeetingInvite> list = new ArrayList<>();
        list.add(temp);
        UserMeetingNamePair x = new  UserMeetingNamePair(acctTwo.getName(), one.getName());
        when(inviteController.sendMeetingInvite(x)).thenReturn(Stringy.success());
       String acctList = inviteController.sendMeetingInvite(x);
        assertEquals(Stringy.success(), acctList);

    }

    @Test
    public void acceptMeetInvites(){
        Location loc = new Location("street", "city", "state", 12345, "country");
        User acctOne = new User("hoiproa0", "hoiproa0", Role.ADMIN);
        Meeting one = new Meeting(acctOne, "first1", "ahahaha", "0000", 1, loc);
        User acctTwo = new User("hoiprao1", "hoiprao1", Role.ADMIN);
        MeetingInvite temp = new MeetingInvite(acctTwo, one, MeetingInviteState.PENDING);
        List<MeetingInvite> list = new ArrayList<>();
        list.add(temp);
        UserMeetingNamePair x = new  UserMeetingNamePair(acctTwo.getName(), one.getName());
        when(inviteController.acceptMeetingInviteNames(x)).thenReturn(Stringy.success());
        String acctList = inviteController.acceptMeetingInviteNames(x);
        assertEquals(Stringy.success(), acctList);

    }




}