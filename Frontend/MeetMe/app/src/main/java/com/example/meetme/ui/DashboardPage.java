package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.DashboardMeetingAdapter;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

import java.util.ArrayList;
import java.util.List;

/**
 * DashboardPage includes logic for recyclerView
 */
public class DashboardPage extends BaseActivity {

    /**
     * user's name
     */
    String username;
    /**
     * used to display a list of meetings.
     */
    RecyclerView recyclerView;

    /**
     * Set up the page and initialize the recyclerview
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        TextView welcomeText = findViewById(R.id.activity_dashboard_text);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);

        Button chatButton = findViewById(R.id.chat_btn);

        // load meetings as interactive cards
        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<Meeting>>(meetings->
        {
            recyclerView = findViewById(R.id.recyclerViewMeeting);

            DashboardMeetingAdapter dashboardMeetingAdapter = new DashboardMeetingAdapter(getApplicationContext(), new ArrayList<Meeting>(meetings));

            recyclerView.setAdapter(dashboardMeetingAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }));

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ChatPage.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });
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
