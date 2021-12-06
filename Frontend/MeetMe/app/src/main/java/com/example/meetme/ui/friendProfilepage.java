package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;
import com.example.meetme.model.UserNamePair;
import com.example.meetme.model.UserShadow;
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
        String friendName = getIntent().getStringExtra("friendname");

        GetUserApi().getUserByName(friendName).enqueue(new SlimCallback<UserShadow>(user -> {
            friend.setText("Name: " + user.getName());
        });

        Button addFriend = findViewById(R.id.activity_friendProfile_addFriend);
        TextView errorMsg = findViewById(R.id.activity_friendProfile_errorMsg);

        GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        String username = globalVariable.getName();

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserNamePair fRequest = new UserNamePair(username, friendName);
                GetUserApi().sendFriendRequest(fRequest).enqueue(new SlimCallback<>(f ->{
                    if (f.getMessage().equals("Success")){
                        errorMsg.setText("Successfully sent a friend request!");
                    }
                    else{
                        errorMsg.setText("Friend request failed: " + f.getReason());
                    }
                }));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onStart(){
        super.onStart();
        TextView errorMsg = findViewById(R.id.activity_friendProfile_errorMsg);
        errorMsg.setText("");
    }
}