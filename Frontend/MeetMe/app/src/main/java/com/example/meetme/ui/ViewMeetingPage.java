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

import com.example.meetme.R;
import com.example.meetme.UserAdapter;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * For viewing an individual meeting an its attributes
 */
public class ViewMeetingPage extends AppCompatActivity {

    /**
     * name of given meeting
     */
    String meetingNameStr = "";

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
        TextView meetingDesc = findViewById(R.id.description);
        TextView meetingDateTime = findViewById(R.id.dateTime);
        TextView meetingLocation = findViewById(R.id.location);
        TextView meetingAdminName = findViewById(R.id.adminName);



        // Get information sent from last activity page
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            meetingName.setText("Unknown");
            return;
        }

        meetingNameStr = extras.getString("meetingName");
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
                myIntent.putExtra("meeting name", meetingNameStr);
                startActivity(myIntent);
            }
        });

        // Get meeting info and display it
        GetMeetingApi().getMeetingByName(meetingNameStr).enqueue(new SlimCallback<Meeting>(meeting -> {
            meetingAdminName.setText(meeting.getAdminName());
            meetingDesc.setText(meeting.getDesc());
            meetingDateTime.setText(meeting.getDateTime());
            meetingLocation.setText(meeting.getCountry());


            recyclerView = findViewById(R.id.participantsRecycler);

            UserAdapter userAdapter = new UserAdapter(meeting.getParticipants());

            recyclerView.setAdapter(userAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }));
    }

}
