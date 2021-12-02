package com.example.meetme.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.api.MeetingApi;
import com.example.meetme.model.Meeting;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CreateMeetingPage includes logic for inputs and buttons
 */
public class CreateMeetingPage extends AppCompatActivity {

    /**
     * The error message displayed to the user
     */
    private TextView errorMsg;

    private MeetingApi m;


    /**
     * Set up the page.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        EditText meetingTitle_textbox = findViewById(R.id.activity_createMeeting_TitleInput);
        EditText meetingDescription_textbox = findViewById(R.id.activity_createMeeting_DescriptionInput);
        EditText meetingTime_textbox = findViewById(R.id.activity_createMeeting_timeInput);
        EditText meetingLocation_textbox = findViewById(R.id.activity_createMeeting_LocationInput);
        EditText meetingDate_textbox = findViewById(R.id.activity_createMeeting_dateInput);

        Button backButton = findViewById(R.id.activity_createMeeting_backButton);
        Button createButton = findViewById(R.id.activity_createMeeting_createMeetingButton);
        Button addparticipants = findViewById(R.id.activity_createMeeting_add_participants);

        errorMsg = findViewById(R.id.activity_createMeeting_errorMsg);
        String username = getIntent().getStringExtra("username");


        addparticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AddParticipantsPage.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String mTitle = meetingTitle_textbox.getText().toString();
                String mDesc = meetingDescription_textbox.getText().toString();
                String mTime = meetingTime_textbox.getText().toString();
                String mDate = meetingDate_textbox.getText().toString();
                String mLocation[] = meetingLocation_textbox.getText().toString().split(",");

                PostMeeting(mTitle, mDesc, mTime, mDate, mLocation);

//                if (errorMsg.getText().toString().equals("Error Message Goes Here") ||
//                        errorMsg.getText().toString().equals("")) {
//                    finish();
//                }
            }
        });
    }


//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_create_meeting;
//    }
//
//    @Override
//    int getLayoutId() {
//        return R.layout.activity_create_meeting;
//    }
//
//    @Override
//    int getNavigationMenuItemId() {
//        return R.id.action_createMeeting;
//    }

    public MeetingApi getMeetingApi(){
        return m;
    }
    public void setMeetingApi(MeetingApi newM){
        m = newM;
    }


    /**
     * Attempts to create a meeting based on user input and will display any errors
     * encountered in the process.
     * @param mTitle meeting title
     * @param mDesc meeting description
     * @param mTime meeting time
     * @param mDate meeting Date
     * @param mLocation An array of strings representing the meeting location in format {St Address, City, State, Zip, Country}
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void PostMeeting(String mTitle, String mDesc, String mTime, String mDate, String[] mLocation){


        try {
            int zipcode = Integer.parseInt(mLocation[3].replaceAll(" ", ""));

            if (mTitle == null || mDesc == null || mTime == null || mDate == null || mLocation.length < 5) {
                throw new IllegalArgumentException("missing or incorrectly formatted arguments");
            }

            int year = Integer.parseInt(mDate.substring(mDate.length() - 4));
            int month = Integer.parseInt(mDate.substring(0,2));
            int day = Integer.parseInt(mDate.substring(3,5));

            int hour = Integer.parseInt(mTime.substring(0,2));
            int min = Integer.parseInt(mTime.substring(mTime.length()-2));

            LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, min);

            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
            //get username of current client
            final String username = globalVariable.getName();

            Meeting meeting = new Meeting(mTitle, username, mDesc, dateTime.toString(), mLocation[0],
                    mLocation[1], mLocation[2], zipcode, mLocation[4]);

            GetMeetingApi().createMeeting(meeting).enqueue(new SlimCallback<>(response ->
            {
//                if (!response.getError().equals("")){
//
//                    //errorMsg.setText("error: " + /*response.getError() +*/ ", msg: "); //+ response.getResponseMessage());
//                }
                errorMsg.getText();
//                errorMsg.setText(response.toString());
            }));
            finish();
        }
        catch (NumberFormatException e){
            errorMsg.setText("Please enter a valid, numeric zip code");
            if (errorMsg.getVisibility() == View.INVISIBLE) {
                errorMsg.setVisibility(View.VISIBLE);
            }
        }
        catch (IllegalArgumentException e){
            errorMsg.setText("Please enter all fields");
            if (errorMsg.getVisibility() == View.INVISIBLE) {
                errorMsg.setVisibility(View.VISIBLE);
            }
        }
        catch (IndexOutOfBoundsException e){
            errorMsg.setText("Please enter a valid location format");
        }
        catch (Exception e){
            errorMsg.setText(e.toString());
            if (errorMsg.getVisibility() == View.INVISIBLE) {
                errorMsg.setVisibility(View.VISIBLE);
            }
        }
    }
}