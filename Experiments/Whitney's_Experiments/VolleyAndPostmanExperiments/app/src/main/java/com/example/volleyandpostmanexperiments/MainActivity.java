package com.example.volleyandpostmanexperiments;

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

        Button StringRequestPage = findViewById(R.id.btnStringRequest);
        Button JSONRequestPage = findViewById(R.id.btnJsonRequest);
        Button IMAGERequestPage = findViewById(R.id.btnImageRequest);

        StringRequestPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logic for new button
                startActivity(new Intent(view.getContext(), MainActivity2.class));
            }
        });

        JSONRequestPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logic for new button
                startActivity(new Intent(view.getContext(), MainActivity3.class));
            }
        });

        IMAGERequestPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logic for new button
                startActivity(new Intent(view.getContext(), MainActivity4.class));
            }
        });
    }
}