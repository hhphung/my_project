package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.User;

import java.util.ArrayList;

public class ViewMeetingPage extends AppCompatActivity {

    String meetingNameStr = "";

    RecyclerView recyclerView;

    private TextView errorMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meeting);

        // link ids
        Button backButton = findViewById(R.id.backButton);

        TextView meetingName = findViewById(R.id.name);
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

        // Get meeting info and display it
        GetMeetingApi().getMeetingById(meetingNameStr).enqueue(new SlimCallback<Meeting>(meeting -> {
            meetingAdminName.setText(meeting.getAdminName());
            recyclerView = findViewById(R.id.participantsRecycler);

//            User testUser = new User("Person", "arst");
//            ArrayList<User> list = new ArrayList<User>();
//            list.add(testUser);
//
//            UserAdapter userAdapter = new UserAdapter(list);
            UserAdapter userAdapter = new UserAdapter(meeting.getParticipants());

            recyclerView.setAdapter(userAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }));
    }
}
