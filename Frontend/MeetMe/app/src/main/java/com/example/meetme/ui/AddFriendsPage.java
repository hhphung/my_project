package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;
import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetme.R;
import com.example.meetme.SearchMeetingAdapter;
import com.example.meetme.UserAdapter;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.MeetingShadow;
import com.example.meetme.model.UserShadow;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsPage extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_page);

        Button searchButton = findViewById(R.id.activity_addFriend_search_btn_to_search);
        EditText usernameInput = findViewById(R.id.activity_addFriend_search_input);
        Button backButton = findViewById(R.id.btn_to_friend_requests);


        GetUserApi().getAllUsers().enqueue(new SlimCallback<List<UserShadow>>(users->
        {
            recyclerView = findViewById(R.id.activity_addFriend_search_results);

            ArrayList<String> results = new ArrayList<String>();

            for (UserShadow u: users) {
                results.add(u.getName());
            }

            UserAdapter userAdapter = new UserAdapter(results);

            recyclerView.setAdapter(userAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        }));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = usernameInput.getText().toString();

                GetUserApi().getSearchResults(userInput).enqueue(new SlimCallback<List<UserShadow>>(users->
                {
                    recyclerView = findViewById(R.id.activity_addFriend_search_results);


                    ArrayList<String> results = new ArrayList<String>();

                    for (UserShadow u: users) {
                        results.add(u.getName());
                    }

                    UserAdapter userAdapter = new UserAdapter(results);

                    recyclerView.setAdapter(userAdapter);


                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                }));
            }
        });
    }
}