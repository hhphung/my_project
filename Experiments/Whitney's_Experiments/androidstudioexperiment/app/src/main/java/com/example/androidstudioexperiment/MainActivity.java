package com.example.androidstudioexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ButtonToMainPage = findViewById(R.id.Activity_main_to_main_page);

        ButtonToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Write logic for button here
                startActivity(new Intent(view.getContext(),MainPage.class));
            }
        });
    }
}