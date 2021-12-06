package com.example.meetme.ui;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;
import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.meetme.R;
import com.example.meetme.api.MeetingApi;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.MeetingInvite;
import com.example.meetme.model.User;
import com.example.meetme.model.UserNamePair;

import java.util.ArrayList;
import java.util.List;

public class MeetingInvitesPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_invites_page);

        Button back = findViewById(R.id.meetingRequestBack);
        TextView MeetingInviteFont = findViewById(R.id.MeetingInviteText);
        ScrollView view = findViewById(R.id.friendRequestscroll);
        LinearLayout layout = findViewById(R.id.MeetingInvitesLayout);


        MeetingInviteFont.setTypeface(null, Typeface. BOLD);
        Context temp = this;
        String name = getIntent().getStringExtra("username");;



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ProfilePage.class);
                myIntent.putExtra("username", name);
                startActivity(myIntent);
            }
        });




        GetMeetingApi().getByUsername(name).enqueue(new SlimCallback<List<MeetingInvite>>(list ->  {



            for(MeetingInvite x : list){
                Button accept = new Button(temp);
                Button reject = new Button(temp);

                accept.setBackgroundResource(R.drawable.correct);
                reject.setBackgroundResource(R.drawable.wrong);
                ViewGroup.LayoutParams buttons = new ViewGroup.LayoutParams(100, 100);
                accept.setLayoutParams(buttons);
                reject.setLayoutParams(buttons);




                LinearLayout request =  new LinearLayout(temp);

                GradientDrawable gradientDrawable=new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#EDE3D5"));
                gradientDrawable.setCornerRadius(15);

                request.setBackground(gradientDrawable);


                ViewGroup.LayoutParams requests= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                request.setLayoutParams(requests);


                TextView content = new TextView(temp);

                content.setText(" Meeting invite from " + x.getUser() + " for "+ x.getMeeting());
                content.setTypeface(null, Typeface. BOLD);
                content.setTextSize(16);


                request.addView(content);
                request.addView(accept);
                request.addView(reject);
                TextView space = new TextView(temp);
                space.setTextSize(16);
                layout.addView(request);
                layout.addView(space);



                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetMeetingApi().rejectMeetingInviteId(x.getId()).enqueue(new SlimCallback<String>(response -> {

                        }));
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });



                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetMeetingApi().acceptMeetingInviteId(x.getId()).enqueue(new SlimCallback<String>(response -> {

                        }));
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });


            }




        }));



    }



}
