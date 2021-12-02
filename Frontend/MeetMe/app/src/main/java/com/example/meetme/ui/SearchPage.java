package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.meetme.SearchMeetingAdapter;
import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

import java.util.ArrayList;
import java.util.List;


/**
 * SearchPage includes logic for recyclerView and Buttons
 */
public class SearchPage extends BaseActivity {
    RecyclerView recyclerView;

    String meetingName;

    /**
     * Sets up the search page. Extends the base activity to gain access to the bottom navigation bar.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText meetingInput = findViewById(R.id.activity_search_input);
        Button searchButton = findViewById(R.id.activity_search_btn_to_search);
        Button backButton = findViewById(R.id.activity_search_btn_to_dash);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        //get username of current client
        final String username = globalVariable.getName();

        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<Meeting>>(meetings->
        {
            recyclerView = findViewById(R.id.activity_search_results);

            recyclerView.setNestedScrollingEnabled(false);

            SearchMeetingAdapter meetingAdapter = new SearchMeetingAdapter(getApplicationContext(), new ArrayList<Meeting>(meetings));

            recyclerView.setAdapter(meetingAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));

        }));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), DashboardPage.class);
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

                    SearchMeetingAdapter meetingAdapter = new SearchMeetingAdapter(getApplicationContext(), new ArrayList<Meeting>(meetings));

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

