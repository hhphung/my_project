package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.model.User;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button createAccountButton = findViewById(R.id.activity_main_btn_to_create_act);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);
        EditText passwordInput = findViewById(R.id.activity_main_password_input);
        EditText secPasswordInput = findViewById(R.id.activity_main_password_input2);
        TextView errTxt = findViewById(R.id.activity_register_err_msg);

        //use edit text to create user
        //will make passwords match later
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(secPasswordInput.getText().toString().equals(passwordInput.getText().toString())) {
                    GetUserApi().createUser(new User(usernameInput.getText().toString(), passwordInput.getText().toString()));
                    Intent myIntent = new Intent(view.getContext(), DashboardPage.class);
                    myIntent.putExtra("username", usernameInput.getText().toString());
                    startActivity(myIntent);
               }else {
                     errTxt.setText("Passwords do not match. Try again");
               }
            }
        });
    }




}