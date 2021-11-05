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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfilePage extends AppCompatActivity {


    /**
     * Current user's username
     */
    String username;

    /**
     * Sets up the page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        Button back = findViewById(R.id.profileBack);
        Button changePassword = findViewById(R.id.profileChangePassword);
        Button changeAval = findViewById(R.id.profileChangeA);
        Button viewFriends = findViewById(R.id.profileViewFriends);
        TextView userName = findViewById(R.id.profileName);
        LinearLayout friends = findViewById(R.id.friends);
        LinearLayout passWordChange = findViewById(R.id.linearLayoutChangePassword);

        username = getIntent().getStringExtra("username");


        GetUserApi().getUserByName("hoi").enqueue(new SlimCallback<User>(user ->{
            userName.setText( user.getName() + " Profile");
        }));





        Context temp = this;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),DashboardPage.class));
            }
        });



        changeAval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),DashboardPage.class));
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
                    confirm.setText("I have not done anything with this yet");
                    confirm.setVisibility(View.INVISIBLE);

                    passWordChange.addView(newPass);
                    passWordChange.addView(confirmnewPass);
                    passWordChange.addView(done);
                    passWordChange.addView(confirm);
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirm.setVisibility(View.VISIBLE);
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

        ;

        List<Button> buttons = new ArrayList<Button>();
        viewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(friends.getChildCount() == 0) {
                    GetUserApi().getFriends("hoi").enqueue(new SlimCallback<Set<User>>(list -> {
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
                }

            }

        });





    }
}
