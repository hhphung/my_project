package com.example.meetme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.R;
import com.example.meetme.model.Meeting;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

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

        errorMsg = findViewById(R.id.activity_createMeeting_errorMsg);

        CheckBox presentation = findViewById(R.id.activity_createMeeting_checkbox_presentation);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTitle = meetingTitle_textbox.getText().toString();
                String mDesc = meetingDescription_textbox.getText().toString();
                String mTime = meetingTime_textbox.getText().toString();
                String mDate = meetingDate_textbox.getText().toString();
                String mLocation[] = meetingLocation_textbox.getText().toString().split(",");
                Boolean presentationVal = presentation.isChecked();

                PostMeeting(mTitle, mDesc, mTime, mDate, mLocation, presentationVal);
//                if (errorMsg.getText().toString().equals("Error Message Goes Here") ||
//                        errorMsg.getText().toString().equals("")) {
//                    finish();
//                }
            }
        });
    }


    /**
     * Attempts to create a meeting based on user input and will display any errors
     * encountered in the process.
     * @param mTitle meeting title
     * @param mDesc meeting description
     * @param mTime meeting time
     * @param mDate meeting Date
     * @param mLocation An array of strings representing the meeting location in format {St Address, City, State, Zip, Country}
     * @param mPresentation a boolean val for whether or not it is a presentation
     */
    protected void PostMeeting(String mTitle, String mDesc, String mTime, String mDate, String[] mLocation, boolean mPresentation){

        try {
            int zipcode = Integer.parseInt(mLocation[3].replaceAll(" ", ""));

            if (mTitle == null || mDesc == null || mTime == null || mDate == null || mLocation.length < 5) {
                throw new IllegalArgumentException("missing or incorrectly formatted arguments");
            }

            String reformatedDate = reformatDate(mDate);
            String reformatedTime = reformatTime(mTime);

            String mDateTime = reformatedDate + "T" + reformatedTime;

            Meeting meeting = new Meeting(mTitle, "test2", mDesc, mDateTime, mLocation[0],
                    mLocation[1], mLocation[2], zipcode, mLocation[4], mPresentation);

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
        catch (Exception e){
            errorMsg.setText(e.toString());
            if (errorMsg.getVisibility() == View.INVISIBLE) {
                errorMsg.setVisibility(View.VISIBLE);
            }
        }
    }

    static String reformatDate(String mDate){
        String result = "";

        if(mDate.length() == 9) {
            result = mDate.substring(mDate.length() - 4);
            result += "-" + mDate.substring(0,4);
        }

        return result;
    }

    static String reformatTime(String mTime){
        String result = "";

        if(mTime.length() == 5)
        {
            result = mTime + ":00";
        }

        if(mTime.length() == 4)
        {
            result = "0" + mTime + ":00";
        }

        return result;
    }
}