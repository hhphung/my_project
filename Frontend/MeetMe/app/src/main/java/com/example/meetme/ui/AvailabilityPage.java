package com.example.meetme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meetme.R;
import com.example.meetme.model.Meeting;
import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Meeting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilityPage extends AppCompatActivity {

    Button confirm;
    Spinner mondayStart, mondayEnd, tuesdayStart, tuesdayEnd, wednesdayStart, wednesdayEnd, thursdayStart, thursdayEnd;
    Spinner fridayStart, fridayEnd, saturdayStart, saturdayEnd, sundayStart, sundayEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability_page);
        init_dropdownMenus();

        confirm = findViewById(R.id.activity_availability_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostAvailability();
            }
        });
    }

    private void init_dropdownMenus(){
        mondayStart = findViewById(R.id.activity_availability_mondayStart);
        mondayEnd = findViewById(R.id.activity_availability_mondayEnd);
        tuesdayStart = findViewById(R.id.activity_availability_tuesdayStart);
        tuesdayEnd = findViewById(R.id.activity_availability_tuesdayEnd);
        wednesdayStart = findViewById(R.id.activity_availability_wednesdayStart);
        wednesdayEnd = findViewById(R.id.activity_availability_wednesdayEnd);
        thursdayStart = findViewById(R.id.activity_availability_thursdayStart);
        thursdayEnd = findViewById(R.id.activity_availability_thursdayEnd);
        fridayStart = findViewById(R.id.activity_availability_fridayStart);
        fridayEnd = findViewById(R.id.activity_availability_fridayEnd);
        saturdayStart = findViewById(R.id.activity_availability_saturdayStart);
        saturdayEnd = findViewById(R.id.activity_availability_saturdayEnd);
        sundayStart = findViewById(R.id.activity_availability_sundayStart);
        sundayEnd = findViewById(R.id.activity_availability_sundayEnd);

        Spinner[] starts = new Spinner[] {mondayStart, tuesdayStart, wednesdayStart, thursdayStart, fridayStart, saturdayStart, sundayStart};
        Spinner[] ends = new Spinner[] {mondayEnd, tuesdayEnd, wednesdayEnd, thursdayEnd, fridayEnd, saturdayEnd, sundayEnd};
        String[] startHours = new String[25];
        String[] endHours = new String[25];
        endHours[0] = "End time";
        startHours[0] = "Start time";
        for (Integer i = 0; i < 24; i++){
            startHours[i+1] = i.toString() + ":00";
            endHours[i+1] = i.toString() + ":00";
        }
        ArrayAdapter<String> endHoursAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, endHours);
        ArrayAdapter<String> startHoursAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, startHours);

        for (int i = 0; i < 7; i++){
            starts[i].setAdapter(startHoursAdapter);
            ends[i].setAdapter(endHoursAdapter);
        }
    }

    protected boolean PostAvailability(){
        startActivity(new Intent(this.getBaseContext(), DashboardPage.class));
        finish();
        return false;
    }
}