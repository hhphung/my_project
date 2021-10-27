package com.example.meetme.ui;

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

import com.example.meetme.MeetingAdapter;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.ArrayList;
import java.util.List;

public class DashboardPage extends BaseActivity {

    String username;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
