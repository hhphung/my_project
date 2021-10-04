package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.User;

public class DashboardPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);
        TextView welcomeText = findViewById(R.id.activity_dashboard_text);
        EditText usernameInput = findViewById(R.id.activity_main_username_input);

        //get user id and username
        GetUserApi().getUser(usernameInput.getText().toString()).enqueue(new SlimCallback<User>(user ->{
            welcomeText.setText("Welcome " + user.getUsername() + "!");
        }));
    }
}