package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetUserApi;
import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.List;

import retrofit2.Call;

public class DashboardPage extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);
        TextView welcomeText = findViewById(R.id.activity_dashboard_text);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);
        Button goToCreateMeeting = findViewById(R.id.activity_dashboard_goToCreateMeeting);
        TextView meetingsList = findViewById(R.id.activity_dashboard_meetingList);
        Button search = findViewById(R.id.activity_dashboard_btn_to_search);

        meetingsList.setGravity(View.TEXT_ALIGNMENT_CENTER);
        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<Meeting>>(meetings1->
        {
            for (Meeting m : meetings1){
                meetingsList.append(m.getName() + "\n");
            }
        }));

        username = getIntent().getStringExtra("username");

        goToCreateMeeting.setText("Create a meeting!");

        //get username and display it
        GetUserApi().getUserByName(username).enqueue(new SlimCallback<User>(user ->{
            welcomeText.setText("Welcome " + user.getName() + "!");
        }));

        //to go to the create meeting page
        goToCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CreateMeetingPage.class));
            }
        });

        //to go to the search page
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SearchPage.class));
            }
        });
    }
}