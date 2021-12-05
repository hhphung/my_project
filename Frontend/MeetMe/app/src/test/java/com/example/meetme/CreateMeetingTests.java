package com.example.meetme;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.api.MeetingApi;
import com.example.meetme.api.apiClientFactory;
import com.example.meetme.model.User;
import com.example.meetme.ui.CreateMeetingPage;
import com.example.meetme.ui.DashboardPage;
import com.example.meetme.ui.RegisterPage;
import com.google.android.material.navigation.NavigationBarView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class CreateMeetingTests {

    @Mock
    private CreateMeetingPage activity;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);


        //Test
        activity = Robolectric.buildActivity(CreateMeetingPage.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void testCreateMeeting()
    {
        // enter text
        // when getAllMeetings, then return a list of meetings.
        // Mock the server response to a meeting already being created --> Test that that is handled appropriately
        //

        EditText title = activity.findViewById(R.id.activity_createMeeting_TitleInput);
        EditText desc = activity.findViewById(R.id.activity_createMeeting_DescriptionInput);
        EditText time = activity.findViewById(R.id.activity_createMeeting_timeInput);
        EditText location = activity.findViewById(R.id.activity_createMeeting_LocationInput);

        title.setText("Test Meeting");
        desc.setText("Test Description: lorem ipsum dolor");
        time.setText("12:00");

        //Enter an invalid location format
        location.setText("Ames");
        activity.findViewById(R.id.activity_createMeeting_createMeetingButton).callOnClick();

        String error = "Please enter a valid location format";
        TextView errMsg = activity.findViewById(R.id.activity_createMeeting_errorMsg);

        assertEquals(error, errMsg.getText());


    }

    @Test
    public void NotNullActivity() throws Exception{
        assertNotNull(activity);
    }


    @Test
    public void MockitoExample(){
        NavigationBarView navbar = mock(NavigationBarView.class);
        List mockedList = mock(List.class);

        when(mockedList.get(0)).thenReturn("first");

        when(navbar.callOnClick()).thenReturn(true);

        navbar.callOnClick();

        verify(navbar).callOnClick();
        verify(mockedList, times(0)).get(0);

    }

}