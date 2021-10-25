package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetUserApi;
import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.List;

public class DashboardPage extends BaseActivity {

    String username;
    TextView meetingsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView welcomeText = findViewById(R.id.activity_dashboard_text);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);
        meetingsList = findViewById(R.id.activity_dashboard_meetingList);
        username = getIntent().getStringExtra("username");

        //get username and display it
        GetUserApi().getUserByName(username).enqueue(new SlimCallback<User>(user ->{
            welcomeText.setText("Welcome " + user.getName() + "!");
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        meetingsList.setText("");

        meetingsList.setGravity(View.TEXT_ALIGNMENT_CENTER);
        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<Meeting>>(meetings1->
        {
            for (Meeting m : meetings1){
                meetingsList.append(m.getName() + "\n");
            }
        }));
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_dashboard_page;
    }


    @Override
    int getLayoutId() {
        return R.layout.activity_dashboard_page;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_dashboard;
    }
}
