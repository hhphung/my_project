package com.example.meetme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.example.meetme.api.AvailabilityApi;
import com.example.meetme.model.Availability;
import com.example.meetme.model.Meeting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
public class MeetingAvailabilityTests {

    @Mock
    private Meeting mockedMeeting;

    private ArrayList<Boolean> monday;
    private ArrayList<Boolean> tuesday;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockedMeeting = mock(Meeting.class);

        monday = new ArrayList<>(168);
        tuesday = new ArrayList<>(168);
    }

    @Test
    public void userAvailabilityDuringMeeting(){

        Mockito.when(mockedMeeting.getStartEndOfMeeting()).thenReturn(new int[]{12,15});

        for (int i = 0; i < 168; i++){
            if (i < 24){
                tuesday.add(false);
            }
            else {
                tuesday.add(true);
            }

            if (i < 12 || i > 15){
                monday.add(false);
            }
            else{
                monday.add(true);
            }
        }

        Availability mondayAvailability = new Availability("Monday", monday.toArray(new Boolean[0]));
        int mondayTimes[] = mockedMeeting.getStartEndOfMeeting();
        boolean mondayResult = mondayAvailability.isAvailableDuringRange(mondayTimes[0], mondayTimes[1]);
        assertTrue(mondayResult);

        Availability tuesdayAvailability = new Availability("Tuesday", tuesday.toArray(new Boolean[0]));
        boolean tuesdayResult = tuesdayAvailability.isAvailableDuringRange(mondayTimes[0], mondayTimes[1]);
        assertFalse(tuesdayResult);

    }

    @Test
    public void meetingStartEndTimes(){
        LocalDateTime tuesDate = LocalDateTime.of(2021, 11, 2, 12, 0);
        Mockito.when(mockedMeeting.getDateTime()).thenReturn(tuesDate.toString());
        Mockito.when(mockedMeeting.getDurationHours()).thenReturn(3);

      /*  Meeting m = new Meeting("Test", "Test", "TestDesc", mockedMeeting.getDateTime(), "123 Duff",
                "Ames", "IA", 50012, "USA", mockedMeeting.getDurationHours());
        int times[] = m.getStartEndOfMeeting();
        int expected[] = {36, 39};

        assertEquals(expected[0], times[0]);
        assertEquals(expected[1], times[1]);
        
       */

    }


}
