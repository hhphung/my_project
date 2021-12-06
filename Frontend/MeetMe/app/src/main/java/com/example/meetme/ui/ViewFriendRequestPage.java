package com.example.meetme.ui;



import static com.example.meetme.api.apiClientFactory.GetUserApi;

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

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;
import com.example.meetme.model.UserNamePair;

import java.util.List;

public class ViewFriendRequestPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend_request_page);

        Button back = findViewById(R.id.FriendRequestBackbtn);
        TextView FriendRequestFont = findViewById(R.id.FriendRequestId);
        ScrollView view = findViewById(R.id.friendRequestscroll);
        LinearLayout layout = findViewById(R.id.FriendRequestlayout);

        Button addFriendPage = findViewById(R.id.toAddFriends);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        FriendRequestFont.setTypeface(null, Typeface. BOLD);
        Context temp = this;
        final String name = globalVariable.getName();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addFriendPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AddFriendsPage.class);
                startActivity(myIntent);
            }
        });


        GetUserApi().getFriendRequestsReceived(name).enqueue(new SlimCallback<List<User>>(list -> {

            for(User x : list){
                Button accept = new Button(temp);
                Button reject = new Button(temp);



                accept.setBackgroundResource(R.drawable.correct);
                reject.setBackgroundResource(R.drawable.wrong);
                ViewGroup.LayoutParams buttons = new ViewGroup.LayoutParams(100, 100);
                accept.setLayoutParams(buttons);
                reject.setLayoutParams(buttons);





                LinearLayout request =  new LinearLayout(temp);

                GradientDrawable gradientDrawable=new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#F9FAE3"));
                gradientDrawable.setCornerRadius(15);

                request.setBackground(gradientDrawable);


                ViewGroup.LayoutParams requests= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                request.setLayoutParams(requests);



                TextView content = new TextView(temp);

                content.setText("  You have a friend request from " + x.getName());
                content.setTypeface(null, Typeface. BOLD);
                content.setTextSize(16);


                request.addView(content);
                request.addView(accept);
                request.addView(reject);
                TextView space = new TextView(temp);
                space.setTextSize(16);
                layout.addView(request);
                layout.addView(space);




                UserNamePair send = new UserNamePair(x.getName(), name);
                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetUserApi().rejectFriendRequestNames(send).enqueue(new SlimCallback<String>(response -> {

                        }));

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });



                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetUserApi().acceptFriendRequestNames(send).enqueue(new SlimCallback<String>(response -> {

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
