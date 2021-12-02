package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetUserApi;
import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetme.MeetingAdapter;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * DashboardPage includes logic for recyclerView
 */
public class DashboardPage extends BaseActivity {

    /**
     * user's name
     */
    String username;
    /**
     * used to display a list of meetings.
     */
    RecyclerView recyclerView;

    /**
     * Set up the page and initialize the recyclerview
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        TextView welcomeText = findViewById(R.id.activity_dashboard_text);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);

        Button chatButton = findViewById(R.id.chat_btn);

        // load meetings as interactive cards
        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<Meeting>>(meetings->
        {
            recyclerView = findViewById(R.id.recyclerViewMeeting);

            MeetingAdapter meetingAdapter = new MeetingAdapter(getApplicationContext(), new ArrayList<Meeting>(meetings));

            recyclerView.setAdapter(meetingAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }));

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ChatPage.class);
                myIntent.putExtra("username", username);
                startActivity(myIntent);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic(username).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Notifications ready for " + username, Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    protected int getContentViewId() {
        return R.layout.activity_dashboard_page;
    }


    @Override
    int getLayoutId() {
        return R.layout.activity_dashboard_page;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_dashboard;
    }
}
