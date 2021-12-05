package com.example.meetme;

import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.EditText;

import com.example.meetme.ui.AddParticipantsPage;
import com.example.meetme.ui.RegisterPage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
public class AddParticipantTests {

    private AddParticipantsPage activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AddParticipantsPage.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void searchManyUsers(){
        EditText usernameEntry = activity.findViewById(R.id.activity_user_search_input);
        usernameEntry.setText("i");

        Button search = activity.findViewById(R.id.activity_addParticipants_search_btn_to_search);
        search.callOnClick();

        ArrayList<Button> searchResults = new ArrayList<Button>(4);

        ArrayList<Button> actList = activity.getSearchResults();

        assertEquals(searchResults.size(), actList.size());
    }

    @Test
    public void searchSpecificUser()
    {
        EditText usernameEntry = activity.findViewById(R.id.activity_user_search_input);
        usernameEntry.setText("Max");

        Button search = activity.findViewById(R.id.activity_addParticipants_search_btn_to_search);
        search.callOnClick();

        ArrayList<Button> searchResults = new ArrayList<Button>(1);

        ArrayList<Button> actList = activity.getSearchResults();

        assertEquals(searchResults.size(), actList.size());
    }
}
