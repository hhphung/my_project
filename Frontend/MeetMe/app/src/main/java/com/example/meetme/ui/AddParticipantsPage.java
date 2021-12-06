package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;
import com.example.meetme.model.UserShadow;

import java.util.ArrayList;
import java.util.List;

public class AddParticipantsPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participants_page);

        Button toCreatePage = findViewById(R.id.activity_addParticipants_btn_to_create);
        Button searchButton = findViewById(R.id.activity_addParticipants_search_btn_to_search);

        EditText searchInput = findViewById(R.id.activity_user_search_input);
        TextView list = findViewById(R.id.list_for_added_users);

        LinearLayout participants = findViewById(R.id.participants);
        Context temporaryContext = this;

        GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        toCreatePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVariable.setUserParticipantsInMeeting(list.getText().toString());
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchInput.getText().toString();

                if(participants.getChildCount() == 0) {
                    GetUserApi().getSearchResults(searchText).enqueue(new SlimCallback<List<UserShadow>>(participantNames -> {
                        for (UserShadow m : participantNames) {
                            Button user = new Button(temporaryContext);
                            user.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(!list.getText().toString().equals(""))
                                    {
                                        String[] users = list.getText().toString().split(", ");
                                        boolean isInString = false;
                                        for(int i = 0; i < users.length; i++)
                                        {
                                            if(users[i].equals(m.getName())){
                                                isInString = true;
                                            }
                                        }
                                        if(!isInString) {
                                            String orig = list.getText().toString();
                                            list.setText(orig + ", " + m.getName());
                                        }
                                    }
                                    else{
                                        list.setText(m.getName());
                                    }
                                }
                            });
                            user.setText(m.getName());
                            participants.addView(user);
                        }
                    }));
                }
                else{
                    participants.removeAllViews();
                }

            }
        });

    }
}