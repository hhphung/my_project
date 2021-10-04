package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;

public class DashboardPage extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);
        TextView welcomeText = findViewById(R.id.activity_dashboard_text);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);
        Button goToCreateMeeting = findViewById(R.id.activity_dashboard_goToCreateMeeting);

        username = getIntent().getStringExtra("username");

        goToCreateMeeting.setText("Create a meeting!");

        //get username and display it
        GetUserApi().getUserByName(username).enqueue(new SlimCallback<User>(user ->{
            welcomeText.setText("Welcome " + user.getUsername() + "!");
        }));

        goToCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CreateMeetingPage.class));
            }
        });
    }
}