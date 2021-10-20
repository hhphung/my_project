package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;

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
                User user = new User(usernameInput.getText().toString(), passwordInput.getText().toString());
                GetUserApi().canLogin(user).enqueue(new SlimCallback<User>(response -> {
                    if (response.getResponse().equals("Success")) {
                        Intent myIntent = new Intent(view.getContext(), DashboardPage.class);
                        myIntent.putExtra("username", usernameInput.getText().toString());
                        startActivity(myIntent);
                    } else {
                        passwordInput.setError("Check Password again");
                        passwordInput.requestFocus();

                        usernameInput.setError("Check username");
                        usernameInput.requestFocus();
                    }
                }));
            }
        });

    }
}