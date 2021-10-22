package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetUserApi;
import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class DashboardPage extends AppCompatActivity {

    String username;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);
        TextView welcomeText = findViewById(R.id.activity_dashboard_text);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);
        Button goToCreateMeeting = findViewById(R.id.activity_dashboard_goToCreateMeeting);

        // load meetings as interactive cards
        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<Meeting>>(meetings->
        {
            recyclerView = findViewById(R.id.recyclerViewMeeting);

            MeetingAdapter meetingAdapter = new MeetingAdapter(getApplicationContext(), new ArrayList<Meeting>(meetings));

            recyclerView.setAdapter(meetingAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }));


        username = getIntent().getStringExtra("username");

        goToCreateMeeting.setText("Create a meeting!");

        //get username and display it
        GetUserApi().getUserByName(username).enqueue(new SlimCallback<User>(user ->{
            welcomeText.setText("Welcome " + user.getName() + "!");
        }));

        goToCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetUserApi().getUserByName(username).enqueue(new SlimCallback<User>(user ->{
                    welcomeText.setText("Welcome " + user.getName() + "!");
                }));

                startActivity(new Intent(view.getContext(), CreateMeetingPage.class));
            }
        });
    }
}
