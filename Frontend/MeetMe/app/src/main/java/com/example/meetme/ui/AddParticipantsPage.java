package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;
import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.meetme.UserAdapter;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.ArrayList;
import java.util.List;

public class AddParticipantsPage extends AppCompatActivity {

    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participants_page);

        Button toCreatePage = findViewById(R.id.activity_addParticipants_btn_to_create);
        Button searchButton = findViewById(R.id.activity_addParticipants_search_btn_to_search);

        EditText searchInput = findViewById(R.id.activity_user_search_input);
        TextView list = findViewById(R.id.list_for_added_users);

        TextView user = findViewById(R.id.name);
        Button addToMeeting = findViewById(R.id.add_participant);
        String username = getIntent().getStringExtra("username");


        GetUserApi().getAllUsers().enqueue(new SlimCallback<List<User>>(users->
        {
            recyclerView = findViewById(R.id.activity_addParticipants_search_results);

            recyclerView.setNestedScrollingEnabled(true);

            UserAdapter userAdapter = new UserAdapter(new ArrayList<User>(users));

            recyclerView.setAdapter(userAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));

        }));

        toCreatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CreateMeetingPage.class);
                myIntent.putExtra("username list", list.getText().toString());
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchInput.getText().toString();

                GetUserApi().getSearchResults(searchText).enqueue(new SlimCallback<List<User>>(users->
                {
                    recyclerView = findViewById(R.id.activity_search_results);

                    UserAdapter userAdapter = new UserAdapter(new ArrayList<User>(users));

                    recyclerView.setAdapter(userAdapter);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                }));
            }
        });

        addToMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((list.getText().toString().equals(""))) {
                    list.setText( user.getText().toString());
                }else{
                    String orig = list.getText().toString();
                    list.setText(orig + ", " + user.getText().toString());
                }
            }
        });
    }
}