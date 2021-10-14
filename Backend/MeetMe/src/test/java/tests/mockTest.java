package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import org.springframework.boot.autoconfigure.security.SecurityProperties;

public class mockTest {



    @Mock
    UserRepository repo;

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

}

