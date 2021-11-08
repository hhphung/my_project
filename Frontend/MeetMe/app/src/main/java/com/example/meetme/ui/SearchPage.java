package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;


import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;

/**
 * SearchPage includes logic for recyclerView and Buttons
 */
public class SearchPage extends BaseActivity {

    /**
     * Sets up the search page. Extends the base activity to gain access to the bottom navigation bar.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView meetingResults = findViewById(R.id.activity_search_results);
        SearchView meetingInput = findViewById(R.id.activity_search_bar);
        Button backButton = findViewById(R.id.activity_search_btn_to_dash);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), DashboardPage.class));
            }
        });

        meetingInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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