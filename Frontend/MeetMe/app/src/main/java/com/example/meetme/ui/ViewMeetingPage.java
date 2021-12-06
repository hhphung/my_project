package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.UserAdapter;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.MeetingShadow;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * For viewing an individual meeting an its attributes
 */
public class ViewMeetingPage extends AppCompatActivity {

    /**
     * for list of Users in a Meeting
     */
    RecyclerView recyclerView;

    /**
     * TextView for errors
     */
    private TextView errorMsg;

    /**
     * Sets up page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);

        // link ids
        Button backButton = findViewById(R.id.backButton);
        Button chatButton = findViewById(R.id.chatBtn);

        TextView meetingName = findViewById(R.id.meeting_name_title_view);
        TextView meetingDesc = findViewById(R.id.viewMeeting_description);
        TextView meetingDateTime = findViewById(R.id.viewMeeting_dateTime);
        TextView meetingLocation = findViewById(R.id.viewMeeting_location);
        TextView meetingAdminName = findViewById(R.id.viewMeeting_adminName);



        // Get information sent from last activity page
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            meetingName.setText("Unknown");
            return;
        }

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        globalVariable.setMeetingName(extras.getString("meetingName"));

        final String meetingNameStr = globalVariable.getMeetingName();
        meetingName.setText(meetingNameStr);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ChatPage.class);
                startActivity(myIntent);
            }
        });

        // Get meeting info and display it
        GetMeetingApi().getMeetingByName(meetingNameStr).enqueue(new SlimCallback<MeetingShadow>(meeting -> {
            String adminName = meeting.getAdmin();
            String desc = meeting.getDescription();
            String dateTime = meeting.getDateTime();
            String Location = meeting.getLocation().getAddress();

            meetingAdminName.setText(adminName);
            meetingDesc.setText(desc);
            meetingDateTime.setText(dateTime);
            meetingLocation.setText(Location);


            recyclerView = findViewById(R.id.participantsRecycler);

            ArrayList<String> participants = new ArrayList<String>();
            participants.addAll(meeting.getUserParticipants());

            UserAdapter userAdapter = new UserAdapter(participants);

            recyclerView.setAdapter(userAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }));
    }

}
