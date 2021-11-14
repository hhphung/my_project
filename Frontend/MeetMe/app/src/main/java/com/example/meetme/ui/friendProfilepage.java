package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;
import com.example.meetme.ui.ProfilePage;

/**
 * friendProfilePage includes logic for displaying the friend info and buttons
 */
public class friendProfilepage extends AppCompatActivity {

    /**
     * Set up the friend's profile page. Get the friend's name from the intent
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profilepage);
        Button back = findViewById(R.id.friendProfileBack);
        TextView friend = findViewById(R.id.friendProfileUsername);
        String username = getIntent().getStringExtra("friendname");
        GetUserApi().getUserByName(username).enqueue(new SlimCallback<User>(user ->{
            friend.setText("Name: " + user.getName());
        }));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfilePage.class));
            }
        });

    }
}