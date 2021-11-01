package com.example.meetme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.R;
import com.example.meetme.api.MeetingApi;
import com.example.meetme.model.Meeting;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateMeetingPage extends AppCompatActivity {

    private TextView errorMsg;
    private MeetingApi m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        EditText meetingTitle_textbox = findViewById(R.id.activity_createMeeting_TitleInput);
        EditText meetingDescription_textbox = findViewById(R.id.activity_createMeeting_DescriptionInput);
        EditText meetingTime_textbox = findViewById(R.id.activity_createMeeting_timeInput);
        EditText meetingLocation_textbox = findViewById(R.id.activity_createMeeting_LocationInput);
        Button backButton = findViewById(R.id.activity_createMeeting_backButton);
        Button createButton = findViewById(R.id.activity_createMeeting_createMeetingButton);
        errorMsg = findViewById(R.id.activity_createMeeting_errorMsg);


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
                String mLocation[] = meetingLocation_textbox.getText().toString().split(",");
                PostMeeting(mTitle, mDesc, mTime, mLocation);
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
    protected void PostMeeting(String mTitle, String mDesc, String mTime, String[] mLocation){

        try {
            int zipcode = Integer.parseInt(mLocation[3].replaceAll(" ", ""));

            if (mTitle == null || mDesc == null || mTime == null || mLocation.length < 5) {
                throw new IllegalArgumentException("missing or incorrectly formatted arguments");
            }
            Meeting meeting = new Meeting(mTitle, "test2", mDesc, mTime, mLocation[0],
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