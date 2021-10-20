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

public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

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

}