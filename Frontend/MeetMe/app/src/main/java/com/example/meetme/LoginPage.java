package com.example.meetme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button toRegisterBtn = findViewById(R.id.activity_login_btn_to_register);
        Button toDashboardBtn = findViewById(R.id.activity_login_btn_to_dash);
        EditText passwordInput = findViewById(R.id.activity_login_password_input);
        EditText usernameInput = findViewById(R.id.activity_login_username_input);

        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),RegisterPage.class));
            }
        });

        toDashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }
}