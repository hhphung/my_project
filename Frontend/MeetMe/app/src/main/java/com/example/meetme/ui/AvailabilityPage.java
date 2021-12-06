package com.example.meetme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meetme.GlobalClass;
import com.example.meetme.R;
import com.example.meetme.model.Meeting;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilityPage extends AppCompatActivity {

    Button confirm;
    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    String username;
    private int currentDay = 0;

    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_page);
        GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        username = globalVariable.getName();
        confirm = (Button) findViewById(R.id.activity_availability_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeDayAvailability();
            }
        });
        fm = getSupportFragmentManager();
        replaceFragment();

    }


    protected void completeDayAvailability(){
        /*
         *
         *  1. Call confirmDayAvailability from the fragment
         *  2. Increment currentDay
         *  3. Replace the fragment with the next day's fragment
         */
        DayAvailabilityFragment fragment =  (DayAvailabilityFragment) fm.findFragmentByTag("availabilityFragment");
        if (fragment.confirmDayAvailability()){
            confirm.setEnabled(false);
        }
        else{
            currentDay++;
            replaceFragment();
        }
    }

    private void replaceFragment(){
        Bundle bundle = new Bundle();
        bundle.putInt("day", currentDay);
        bundle.putString("username", username);
        fm.beginTransaction()
                .replace(R.id.fragment_container_view, DayAvailabilityFragment.class, bundle, "availabilityFragment")
                .setReorderingAllowed(true)
                .addToBackStack(days[currentDay])
                .commit();
        TextView t = findViewById(R.id.fragment_availability_title);
        //t.setText("Set Availability for " + days[currentDay] + "s");
    }
}