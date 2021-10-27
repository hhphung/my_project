package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;
import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;


import com.example.meetme.MeetingAdapter;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BaseActivity {
    RecyclerView recyclerView;

    String meetingName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText meetingInput = findViewById(R.id.activity_search_input);
        Button searchButton = findViewById(R.id.activity_search_btn_to_search);
        Button backButton = findViewById(R.id.activity_search_btn_to_dash);

        String username = getIntent().getStringExtra("username");

        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<Meeting>>(meetings->
        {
            recyclerView = findViewById(R.id.activity_search_results);

            recyclerView.setNestedScrollingEnabled(false);

            MeetingAdapter meetingAdapter = new MeetingAdapter(getApplicationContext(), new ArrayList<Meeting>(meetings));

            recyclerView.setAdapter(meetingAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));

        }));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), DashboardPage.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meetingName = meetingInput.getText().toString();

                GetMeetingApi().getResults(meetingName).enqueue(new SlimCallback<List<Meeting>>(meetings->
                {
                    recyclerView = findViewById(R.id.activity_search_results);

                    MeetingAdapter meetingAdapter = new MeetingAdapter(getApplicationContext(), new ArrayList<Meeting>(meetings));

                    recyclerView.setAdapter(meetingAdapter);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                }));
            }
        });



    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search_page;
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_search_page;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_search;
    }

}

