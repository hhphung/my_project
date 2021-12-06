package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;
import com.example.meetme.model.UserShadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ProfilePage includes logic for inputs and buttons
 */
public class ProfilePage extends BaseActivity {



    /**
     * Sets up the page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button back = findViewById(R.id.profileBack);
        Button changePassword = findViewById(R.id.profileChangePassword);
        Button changeAval = findViewById(R.id.profileChangeA);
        Button viewFriends = findViewById(R.id.profileViewFriends);
        TextView userNameDisplay = findViewById(R.id.profileName);
        LinearLayout friends = findViewById(R.id.friends);
        ScrollView friendsContainer = findViewById(R.id.friendsScrollView);
        LinearLayout passWordChange = findViewById(R.id.linearLayoutChangePassword);
        Button toFriendRequests = findViewById(R.id.toFriendRequest);
        Button toMeetingInvites = findViewById(R.id.toMeetingInvites);
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        //get username of current client
        final String username = globalVariable.getName();


        GetUserApi().getUserByName(username).enqueue(new SlimCallback<UserShadow>(user ->{
            userNameDisplay.setText(user.getName());
        }));


        Context temp = this;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        changeAval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AvailabilityPage.class);
                startActivity(myIntent);
            }
        });



        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(passWordChange.getLayoutParams().height == 0) {
                    passWordChange.getLayoutParams().height = 450;
                    passWordChange.requestLayout();

                    EditText newPass = new EditText(temp);
                    EditText confirmnewPass = new EditText(temp);
                    Button done = new Button(temp);
                    TextView confirm = new TextView(temp);
                    newPass.setHint("New Password");
                    confirmnewPass.setHint("Confirm Password");
                    done.setText("Done");
                    confirm.setVisibility(View.INVISIBLE);

                    passWordChange.addView(newPass);
                    passWordChange.addView(confirmnewPass);
                    passWordChange.addView(done);
                    passWordChange.addView(confirm);
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(confirmnewPass.getText().toString().equals(newPass.getText().toString()) && !(confirmnewPass.getText().toString().equals(""))) {
                                User user = new User(username, newPass.getText().toString());
                                GetUserApi().changePassword(user).enqueue(new SlimCallback<>(user1 ->{
                                    if(user1.getResponse().equals("Success")){
                                        confirm.setText("Successfully changed password!");
                                    }
                                    else{
                                        confirm.setText("Server Failure: " + user1.getResponse());
                                    }
                                }));
                                confirm.setVisibility(View.VISIBLE);

                            }else{
                                //possible error messages
                                if(confirmnewPass.getText().toString().equals(""))
                                {
                                    confirm.setText("Cannot be empty");
                                }else {
                                    confirm.setText("Passwords do not match. Try again");
                                }
                                confirm.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }
                else{
                    passWordChange.getLayoutParams().height = 0;
                    passWordChange.removeAllViews();
                    passWordChange.requestLayout();
                }
            }
        });

        List<Button> buttons = new ArrayList<Button>();
        viewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(friends.getChildCount() == 0) {
                    friendsContainer.getLayoutParams().height = 150;
                    GetUserApi().getFriends(username).enqueue(new SlimCallback<Set<User>>(list -> {
                        for (User m : list) {
                            Button friend = new Button(temp);
                            friend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent myIntent = new Intent(view.getContext(), friendProfilepage.class);
                                    myIntent.putExtra("friendname", friend.getText().toString());
                                    startActivity(myIntent);
                                }
                            });
                            buttons.add(friend);
                            friend.setText(m.getName());
                            friends.addView(friend);
                        }
                    }));
                }
                else{
                    friends.removeAllViews();
                    friendsContainer.getLayoutParams().height = 0;
                }
            }
        });

        toFriendRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ViewFriendRequestPage.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });

        toMeetingInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MeetingInvitesPage.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_profile_page;
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_profile_page;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_profile;
    }
}
