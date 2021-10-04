package com.example.meetme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.model.Meeting;

public class CreateMeetingPage extends AppCompatActivity {

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
                String mLocation = meetingLocation_textbox.getText().toString();

                if (mTitle != "" && mDesc != "" && mTime != "" && mLocation != "") {
                    GetMeetingApi().createMeeting(new Meeting(mTitle, mDesc, mLocation, mTime));
                    finish();
                }
                else{
                    TextView errorMessage = new TextView(view.getContext());
                    errorMessage.setTextColor(0xFFFF0000);
                    errorMessage.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    errorMessage.setText("All fields are required");
                }
            }
        });
    }
}