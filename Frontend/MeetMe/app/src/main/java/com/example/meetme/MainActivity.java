package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetme.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createAccountButton = findViewById(R.id.activity_main_btn_to_create_act);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);
        EditText passwordInput = findViewById(R.id.activity_main_password_input);

        //use edit text to create user
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetUserApi().createUser(new User(usernameInput.getText().toString(), passwordInput.getText().toString()))
                startActivity(new Intent(view.getContext(), DashboardPage.class));
            }
        });
    }




}