package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;
import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;


import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        ListView meetingResults = findViewById(R.id.activity_search_results);
        SearchView meetingInput = findViewById(R.id.activity_search_bar);
        Button backButton = findViewById(R.id.activity_search_btn_to_dash);

        String username = getIntent().getStringExtra("username");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), DashboardPage.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });

        meetingInput.setInputType(InputType.TYPE_CLASS_TEXT);

        meetingInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //do something on text submit
                ArrayAdapter<String> arr;
                String err[] = {"No Results", ""};
                ArrayList<String> tempResults = null;

                if (meetingInput.toString() == "") {

                    GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<Meeting>>(sResults -> {
                        for (Meeting m : sResults) {
                            String tempString = m.getName();
                            tempResults.add(tempString);
                        }
                    }));

                    String result[] = tempResults.toArray(new String[tempResults.size()]);

                    arr = new ArrayAdapter<String>(getBaseContext(), R.layout.activity_search_page, result);

                } else {


                    GetMeetingApi().getResults(meetingInput.toString()).enqueue(new SlimCallback<List<Meeting>>(sResults -> {
                        for (Meeting m : sResults) {
                            String tempString = m.getName();
                            tempResults.add(tempString);
                        }
                    }));

                    String result[] = tempResults.toArray(new String[tempResults.size()]);

                    if (result[0] != null) {
                        arr = new ArrayAdapter<String>(getBaseContext(), R.layout.activity_search_page, result);
                    } else {
                        arr = new ArrayAdapter<String>(getBaseContext(), R.layout.activity_search_page, err);
                    }

                }
                meetingResults.setAdapter(arr);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do nothing when text changes
                return false;
            }
        });


                meetingResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(view.getContext(), CreateMeetingPage.class); //eventually will go to view meeting page
                        String message = meetingInput.toString();
                        intent.putExtra("Meeting name", message);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                });
            }
        }
