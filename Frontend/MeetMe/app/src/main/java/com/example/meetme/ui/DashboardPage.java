package com.example.meetme.ui;

import static android.app.PendingIntent.getActivity;
import static com.example.meetme.api.apiClientFactory.GetUserApi;
import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.meetme.model.MeetingShadow;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetme.DashboardMeetingAdapter;
import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

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

        TextView welcomeText = findViewById(R.id.activity_dashboard_text);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        username = globalVariable.getName();
        // load meetings as interactive cards
        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<MeetingShadow>>(meetings->
        {
            recyclerView = findViewById(R.id.recyclerViewMeeting);
            ArrayList<MeetingShadow> allMeetings = new ArrayList<MeetingShadow>(meetings);
            ArrayList<MeetingShadow> participatingMeetings = new ArrayList<MeetingShadow>();

            Log.d("Username is: ", username);
            for (MeetingShadow m: allMeetings){
                for (String participant : m.getUserParticipants()){
                    Log.d("Participants for : " + m.getName(), ": " + participant);
                    if (participant.equals(username)){
                        participatingMeetings.add(m);
                    }
                }
            }

            DashboardMeetingAdapter dashboardMeetingAdapter = new DashboardMeetingAdapter(getApplicationContext(), participatingMeetings );

            recyclerView.setAdapter(dashboardMeetingAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }));


        FirebaseMessaging.getInstance().subscribeToTopic(username).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Notifications ready for " + username, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        GetMeetingApi().getAllMeetings().enqueue(new SlimCallback<List<MeetingShadow>>(meetings->
        {
            recyclerView = findViewById(R.id.recyclerViewMeeting);
            ArrayList<MeetingShadow> allMeetings = new ArrayList<MeetingShadow>(meetings);
            ArrayList<MeetingShadow> participatingMeetings = new ArrayList<MeetingShadow>();

            Log.d("Username is: ", username);
            for (MeetingShadow m: allMeetings){
                for (String participant : m.getUserParticipants()){
                    Log.d("Participants for : " + m.getName(), ": " + participant);
                    if (participant.equals(username)){
                        participatingMeetings.add(m);
                    }
                }
            }

            DashboardMeetingAdapter dashboardMeetingAdapter = new DashboardMeetingAdapter(getApplicationContext(), participatingMeetings );

            recyclerView.setAdapter(dashboardMeetingAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }));
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
